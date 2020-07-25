package com.danilkomyshev.byteshareutil

import android.os.Parcel
import android.os.Parcelable


fun Parcelable.toByteArray(): ByteArray {
    val parcel = Parcel.obtain()
    this.writeToParcel(parcel, 0)
    val bytes = parcel.marshall()
    parcel.recycle()
    return bytes
}

object ParcelableUtils {

    fun <T : Parcelable> parcelableFromBytes(bytes: ByteArray, creator: Parcelable.Creator<T>): T {
        val parcel = parcelFromBytes(bytes)
        return creator.createFromParcel(parcel)
    }

    private fun parcelFromBytes(bytes: ByteArray): Parcel {
        val parcel = Parcel.obtain()
        parcel.unmarshall(bytes, 0, bytes.size)
        parcel.setDataPosition(0)
        return parcel
    }
}

