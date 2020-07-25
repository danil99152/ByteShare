package com.danilkomyshev.byteshare

import android.app.Activity
import android.content.Intent
import android.nfc.NdefMessage
import android.nfc.NdefRecord
import android.nfc.NfcAdapter
import android.nfc.NfcEvent
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.danilkomyshev.byteshare.database.TaskEntity
import com.danilkomyshev.byteshare.di.ViewModelFactory
import com.danilkomyshev.byteshare.util.toast
import com.danilkomyshev.byteshareutil.toByteArray
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject


class MainActivity : AppCompatActivity(), LifecycleOwner, HasActivityInjector, NfcAdapter.CreateNdefMessageCallback {

    private val lifecycle = LifecycleRegistry(this)

    @Inject lateinit var activityInjector: DispatchingAndroidInjector<Activity>
    @Inject lateinit var viewModelFactory: ViewModelFactory
    private lateinit var tasksViewModel: TasksViewModel

    private lateinit var tasksRv: RecyclerView
    private lateinit var tasksAdapter: TasksAdapter
    private lateinit var addFab: FloatingActionButton

    private var nfcAdapter: NfcAdapter? = null

    override fun getLifecycle(): Lifecycle = lifecycle

    override fun activityInjector(): AndroidInjector<Activity> = activityInjector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tasksRv = findViewById(R.id.tasksRv)
        tasksRv.layoutManager = LinearLayoutManager(this)
        tasksRv.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        tasksAdapter = TasksAdapter(onTaskClickListener = object : TasksAdapter.OnTaskClickListener {
            override fun onClicked(task: TaskEntity) {
                tasksViewModel.updateTask(task)
            }
        })
        tasksRv.adapter = tasksAdapter

        addFab = findViewById(R.id.fab)
        addFab.setOnClickListener {
            AddTaskDialogFragment().show(supportFragmentManager, null)
        }

        tasksViewModel = viewModelFactory.create(TasksViewModel::class.java)

        tasksViewModel.tasks.observe(this, Observer { tasks ->
            tasks?.let {
                tasksAdapter.updateAll(it)
            }
        })

        nfcAdapter = NfcAdapter.getDefaultAdapter(this)?.apply {
            if (!this.isEnabled) {
                toast(R.string.enable_nfc)
                startActivity(Intent(Settings.ACTION_NFC_SETTINGS))
            } else if (!this.isNdefPushEnabled) {
                toast(R.string.enable_beam)
                startActivity(Intent(Settings.ACTION_NFCSHARING_SETTINGS))
            }
        }
        if (nfcAdapter == null) {
            toast(R.string.nfc_not_available)
        } else {
            nfcAdapter?.setNdefPushMessageCallback(this, this)
        }
    }

    override fun createNdefMessage(nfcEvent: NfcEvent?): NdefMessage {
        val records = tasksAdapter.tasks
            .map { NdefRecord.createMime(BuildConfig.CUSTOM_MIME_TYPE, it.toByteArray()) }
            .toTypedArray()
        return NdefMessage(records)
    }
}