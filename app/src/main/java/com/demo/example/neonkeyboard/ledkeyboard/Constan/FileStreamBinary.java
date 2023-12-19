package com.demo.example.neonkeyboard.ledkeyboard.Constan;

import android.content.Context;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;


public class FileStreamBinary {
    private FileInputStream FIS = null;
    private FileOutputStream FOS = null;
    private DataInputStream IN = null;
    private DataOutputStream OUT = null;

    public boolean openFileRead(Context context, String str) {
        try {
            this.FIS = context.openFileInput(str);
            this.IN = new DataInputStream(this.FIS);
            return true;
        } catch (Exception unused) {
            close();
            return false;
        }
    }

    public boolean openFileWrite(Context context, String str) {
        try {
            this.FOS = context.openFileOutput(str, 0);
            this.OUT = new DataOutputStream(this.FOS);
            return true;
        } catch (Exception unused) {
            return false;
        }
    }

    public void close() {
        try {
            DataInputStream dataInputStream = this.IN;
            if (dataInputStream != null) {
                dataInputStream.close();
            }
            DataOutputStream dataOutputStream = this.OUT;
            if (dataOutputStream != null) {
                dataOutputStream.close();
            }
        } catch (Exception unused) {
        }
    }

    public int readInt() {
        try {
            return this.IN.readInt();
        } catch (Exception unused) {
            return 0;
        }
    }

    public int readShort() {
        try {
            return this.IN.readShort();
        } catch (Exception unused) {
            return 0;
        }
    }

    public float readFloat() {
        try {
            return this.IN.readFloat();
        } catch (Exception unused) {
            return 0.0f;
        }
    }

    public boolean readBool() {
        try {
            return this.IN.readBoolean();
        } catch (Exception unused) {
            return false;
        }
    }

    public String readString() {
        try {
            int readInt = this.IN.readInt();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < readInt; i++) {
                sb.append(this.IN.readChar());
            }
            return sb.toString();
        } catch (Exception unused) {
            return "";
        }
    }

    public void writeInt(int i) {
        try {
            this.OUT.writeInt(i);
        } catch (Exception unused) {
        }
    }

    public void writeFloat(float f) {
        try {
            this.OUT.writeFloat(f);
        } catch (Exception unused) {
        }
    }

    public void writeBool(boolean z) {
        try {
            this.OUT.writeBoolean(z);
        } catch (Exception unused) {
        }
    }

    public void writeString(String str) {
        try {
            this.OUT.writeInt(str.length());
            this.OUT.writeChars(str);
        } catch (Exception unused) {
        }
    }
}
