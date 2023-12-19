package com.demo.example.neonkeyboard.ledkeyboard.Constan;

import android.content.Context;


public final class FileSerializer extends FileStreamBinary {
    private static final int HEADER = 2012;
    private int FileVersion = 0;

    @Override 
    public boolean openFileRead(Context context, String str) {
        if (!super.openFileRead(context, str)) {
            return false;
        }
        if (readInt() != HEADER) {
            close();
            return false;
        }
        this.FileVersion = readInt();
        return true;
    }

    public boolean openFileWrite(Context context, String str, int i) {
        if (!super.openFileWrite(context, str)) {
            return false;
        }
        writeInt(HEADER);
        writeInt(i);
        return true;
    }

    public int readInt(int i, int i2) {
        return (i2 == 0 || this.FileVersion >= i2) ? super.readInt() : i;
    }

    public float readFloat(float f, int i) {
        return (i == 0 || this.FileVersion >= i) ? super.readFloat() : f;
    }

    public boolean readBool(boolean z, int i) {
        return (i == 0 || this.FileVersion >= i) ? super.readBool() : z;
    }

    public String readString(String str, int i) {
        return (i == 0 || this.FileVersion >= i) ? super.readString() : str;
    }
}
