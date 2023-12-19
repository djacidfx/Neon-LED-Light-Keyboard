package com.demo.example.neonkeyboard.ledkeyboard.MyGif.holder;

import android.content.ClipDescription;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.view.inputmethod.InputConnectionCompat;
import androidx.core.view.inputmethod.InputContentInfoCompat;

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import com.demo.example.neonkeyboard.R;
import com.demo.example.neonkeyboard.ledkeyboard.Constan.Utils;
import com.demo.example.neonkeyboard.ledkeyboard.MyGif.presenter.IFetchGifDimension;
import com.demo.example.neonkeyboard.ledkeyboard.MyGif.utils.ColorPalette;
import com.demo.example.neonkeyboard.ledkeyboard.MyGif.utils.WeakRefOnPreDrawListener;
import com.demo.example.neonkeyboard.ledkeyboard.MyKeyboard.SoftKeyboard;
import com.tenor.android.core.constant.ContentFormats;
import com.tenor.android.core.constant.MediaCollectionFormats;
import com.tenor.android.core.loader.GlideTaskParams;
import com.tenor.android.core.loader.WeakRefContentLoaderTaskListener;
import com.tenor.android.core.loader.gif.GifLoader;
import com.tenor.android.core.model.impl.Result;
import com.tenor.android.core.util.AbstractListUtils;
import com.tenor.android.core.view.IBaseView;
import com.tenor.android.core.widget.viewholder.StaggeredGridLayoutItemViewHolder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;


public class GifSearchItemVH<CTX extends IBaseView> extends StaggeredGridLayoutItemViewHolder<CTX> {
    public IFetchGifDimension mFetchGifDimensionListener;
    private final ImageView mImageView;
    public File mOutput;
    public String mPath;
    private Result mResult;
    private WeakReference<Context> mWeakRef;

    public GifSearchItemVH(View view, CTX ctx) {
        super(view, ctx);
        this.mImageView = (ImageView) view.findViewById(R.id.gdi_iv_image);
    }

    public void renderGif(Result result, int i) {
        if (result != null && hasContext()) {
            this.mResult = result;
            if (!AbstractListUtils.isEmpty(result.getMedias())) {
                GlideTaskParams glideTaskParams = new GlideTaskParams(this.mImageView, result.getMedias().get(0).get(MediaCollectionFormats.GIF_TINY).getUrl());
                String placeholderColorHex = result.getPlaceholderColorHex();
                if (Color.parseColor(placeholderColorHex) != -16777216) {
                    glideTaskParams.setPlaceholder(placeholderColorHex);
                } else {
                    glideTaskParams.setPlaceholder(getContext().getResources().getColor(ColorPalette.getRandomColorResId(i)));
                }
                glideTaskParams.setListener(new WeakRefContentLoaderTaskListener<CTX, ImageView>(getRef()) { 
                    public void failure(CTX ctx, ImageView imageView, Drawable drawable) {
                        failure(ctx, imageView, drawable);
                    }

                    public void success(CTX ctx, ImageView imageView, Drawable drawable) {
                        success(ctx, imageView, drawable);

                    }


                });
                GifLoader.loadGif(getContext(), glideTaskParams);
                this.itemView.setOnClickListener(new View.OnClickListener() { 
                    @Override 
                    public void onClick(View view) {
                        GifSearchItemVH.this.onClicked();
                    }
                });
            }
        }
    }

    public void setFetchGifHeightListener(IFetchGifDimension iFetchGifDimension) {
        this.mFetchGifDimensionListener = iFetchGifDimension;
    }

    public boolean setupViewHolder(Result result, int i) {
        if (result == null || !hasContext()) {
            return false;
        }
        postChangeGifViewDimension(this.itemView, result, i);
        return true;
    }

    private void postChangeGifViewDimension(View view, final Result result, final int i) {
        final float aspectRatio = result.getMedias().get(0).get(MediaCollectionFormats.GIF_TINY).getAspectRatio();
        view.getViewTreeObserver().addOnPreDrawListener(new WeakRefOnPreDrawListener<View>(view) {
            @Override
            
            public boolean onPreDraw(View view2) {
                view2.getViewTreeObserver().removeOnPreDrawListener(this);
                ViewGroup.LayoutParams layoutParams = view2.getLayoutParams();
                if (i == 1) {
                    layoutParams.height = Math.round(((float) view2.getMeasuredWidth()) / aspectRatio);
                }
                if (i == 0) {
                    layoutParams.width = Math.round(((float) view2.getMeasuredHeight()) * aspectRatio);
                }
                if (GifSearchItemVH.this.mFetchGifDimensionListener != null) {
                    GifSearchItemVH.this.mFetchGifDimensionListener.onReceiveViewHolderDimension(result.getId(), layoutParams.width, layoutParams.height, i);
                }
                view2.setLayoutParams(layoutParams);
                return true;
            }
        });
    }

    private void commitGifImage(Uri uri) {
        InputConnectionCompat.commitContent(SoftKeyboard.Instance.getCurrentInputConnection(), SoftKeyboard.Instance.getCurrentInputEditorInfo(), new InputContentInfoCompat(uri, new ClipDescription("", new String[]{ContentFormats.IMAGE_GIF}), null), Build.VERSION.SDK_INT >= 25 ? 1 : 0, null);
    }

    public void failure() {
        Toast.makeText(SoftKeyboard.Instance, "Fail to send", Toast.LENGTH_SHORT).show();
    }

    public void success(Uri uri) {
        if (uri != null) {
            commitGifImage(uri);
        }
    }

    public void onClicked() {
        this.mWeakRef = new WeakReference<>(SoftKeyboard.Instance);
        this.mOutput = getOutputPath();
        String url = this.mResult.getMedias().get(0).get(MediaCollectionFormats.GIF_MEDIUM).getUrl();
        if (this.mWeakRef.get() == null) {
            return;
        }
        if (!TextUtils.isEmpty(url)) {
            SimpleTarget<byte[]> r1 = new SimpleTarget<byte[]>() { 





                public void onResourceReady(byte[] bArr, GlideAnimation<? super byte[]> glideAnimation) {
                    try {
                        FileOutputStream fileOutputStream = new FileOutputStream(GifSearchItemVH.this.mOutput);
                        fileOutputStream.write(bArr);
                        fileOutputStream.close();
                    } catch (IOException e) {
                        Toast.makeText(SoftKeyboard.Instance, "Not Supported", Toast.LENGTH_SHORT).show();
                        onLoadFailed(e, null);
                        GifSearchItemVH.this.failure();
                    }
                    GifSearchItemVH gifSearchItemVH = GifSearchItemVH.this;
                    gifSearchItemVH.success(gifSearchItemVH.getUri());
                }
            };
            DrawableTypeRequest<String> load = Glide.with(this.mWeakRef.get()).load(url);
            load.diskCacheStrategy(DiskCacheStrategy.SOURCE);
            load.asGif().toBytes().into(r1);
            return;
        }
        throw new IllegalArgumentException("url cannot be empty");
    }

    private File getOutputPath() {
        File file = new File(Utils.getGifStorageDir(), "gif_" + Utils.makeFilename() + ".gif");
        if (!file.exists() || file.delete()) {
            return file;
        }
        return null;
    }

    public Uri getUri() {
        if (Build.VERSION.SDK_INT < 24) {
            return Uri.fromFile(this.mOutput);
        }
        return Utils.getImageContentUri(this.mWeakRef.get(), this.mOutput);
    }
}