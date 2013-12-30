/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: D:\\test\\zouni\\src\\com\\zounikeji\\AIDL\\forService.aidl
 */
package com.zounikeji.AIDL;
public interface forService extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.zounikeji.AIDL.forService
{
private static final java.lang.String DESCRIPTOR = "com.zounikeji.AIDL.forService";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.zounikeji.AIDL.forService interface,
 * generating a proxy if needed.
 */
public static com.zounikeji.AIDL.forService asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.zounikeji.AIDL.forService))) {
return ((com.zounikeji.AIDL.forService)iin);
}
return new com.zounikeji.AIDL.forService.Stub.Proxy(obj);
}
@Override public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(DESCRIPTOR);
return true;
}
case TRANSACTION_registerTestCall:
{
data.enforceInterface(DESCRIPTOR);
com.zounikeji.AIDL.forActivity _arg0;
_arg0 = com.zounikeji.AIDL.forActivity.Stub.asInterface(data.readStrongBinder());
this.registerTestCall(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_unregisterTestCall:
{
data.enforceInterface(DESCRIPTOR);
com.zounikeji.AIDL.forActivity _arg0;
_arg0 = com.zounikeji.AIDL.forActivity.Stub.asInterface(data.readStrongBinder());
this.unregisterTestCall(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_invokCallBack:
{
data.enforceInterface(DESCRIPTOR);
this.invokCallBack();
reply.writeNoException();
return true;
}
case TRANSACTION_sendEmptyMessage:
{
data.enforceInterface(DESCRIPTOR);
this.sendEmptyMessage();
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.zounikeji.AIDL.forService
{
private android.os.IBinder mRemote;
Proxy(android.os.IBinder remote)
{
mRemote = remote;
}
@Override public android.os.IBinder asBinder()
{
return mRemote;
}
public java.lang.String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
@Override public void registerTestCall(com.zounikeji.AIDL.forActivity cdb) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder((((cdb!=null))?(cdb.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_registerTestCall, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void unregisterTestCall(com.zounikeji.AIDL.forActivity cdb) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder((((cdb!=null))?(cdb.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_unregisterTestCall, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void invokCallBack() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_invokCallBack, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void sendEmptyMessage() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_sendEmptyMessage, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_registerTestCall = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_unregisterTestCall = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_invokCallBack = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_sendEmptyMessage = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
}
public void registerTestCall(com.zounikeji.AIDL.forActivity cdb) throws android.os.RemoteException;
public void unregisterTestCall(com.zounikeji.AIDL.forActivity cdb) throws android.os.RemoteException;
public void invokCallBack() throws android.os.RemoteException;
public void sendEmptyMessage() throws android.os.RemoteException;
}
