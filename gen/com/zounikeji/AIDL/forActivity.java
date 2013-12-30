/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: D:\\test\\zouni\\src\\com\\zounikeji\\AIDL\\forActivity.aidl
 */
package com.zounikeji.AIDL;
public interface forActivity extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements com.zounikeji.AIDL.forActivity
{
private static final java.lang.String DESCRIPTOR = "com.zounikeji.AIDL.forActivity";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an com.zounikeji.AIDL.forActivity interface,
 * generating a proxy if needed.
 */
public static com.zounikeji.AIDL.forActivity asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof com.zounikeji.AIDL.forActivity))) {
return ((com.zounikeji.AIDL.forActivity)iin);
}
return new com.zounikeji.AIDL.forActivity.Stub.Proxy(obj);
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
case TRANSACTION_performAction:
{
data.enforceInterface(DESCRIPTOR);
this.performAction();
reply.writeNoException();
return true;
}
case TRANSACTION_UIrefresh:
{
data.enforceInterface(DESCRIPTOR);
long _arg0;
_arg0 = data.readLong();
this.UIrefresh(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_refreshView:
{
data.enforceInterface(DESCRIPTOR);
this.refreshView();
reply.writeNoException();
return true;
}
case TRANSACTION_CustomDialogShow1:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.CustomDialogShow1(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_CustomDialogShow2:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.CustomDialogShow2(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_CustomDialogShow3:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.CustomDialogShow3(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_CustomDialogShow4:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.CustomDialogShow4(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_CustomDialogShow5:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.CustomDialogShow5(_arg0);
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements com.zounikeji.AIDL.forActivity
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
@Override public void performAction() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_performAction, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void UIrefresh(long i) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeLong(i);
mRemote.transact(Stub.TRANSACTION_UIrefresh, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void refreshView() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_refreshView, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void CustomDialogShow1(int msg) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(msg);
mRemote.transact(Stub.TRANSACTION_CustomDialogShow1, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void CustomDialogShow2(int msg) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(msg);
mRemote.transact(Stub.TRANSACTION_CustomDialogShow2, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void CustomDialogShow3(int msg) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(msg);
mRemote.transact(Stub.TRANSACTION_CustomDialogShow3, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void CustomDialogShow4(int msg) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(msg);
mRemote.transact(Stub.TRANSACTION_CustomDialogShow4, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void CustomDialogShow5(int msg) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(msg);
mRemote.transact(Stub.TRANSACTION_CustomDialogShow5, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_performAction = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_UIrefresh = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_refreshView = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_CustomDialogShow1 = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_CustomDialogShow2 = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
static final int TRANSACTION_CustomDialogShow3 = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
static final int TRANSACTION_CustomDialogShow4 = (android.os.IBinder.FIRST_CALL_TRANSACTION + 6);
static final int TRANSACTION_CustomDialogShow5 = (android.os.IBinder.FIRST_CALL_TRANSACTION + 7);
}
public void performAction() throws android.os.RemoteException;
public void UIrefresh(long i) throws android.os.RemoteException;
public void refreshView() throws android.os.RemoteException;
public void CustomDialogShow1(int msg) throws android.os.RemoteException;
public void CustomDialogShow2(int msg) throws android.os.RemoteException;
public void CustomDialogShow3(int msg) throws android.os.RemoteException;
public void CustomDialogShow4(int msg) throws android.os.RemoteException;
public void CustomDialogShow5(int msg) throws android.os.RemoteException;
}
