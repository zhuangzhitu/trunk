package cn.cassan.pm.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.io.IOException;

import cn.cassan.pm.AppConfig;
import cn.cassan.pm.AppContext;
import cn.cassan.pm.base.BaseActivity;
import cn.cassan.pm.ui.dialog.ImageMenuDialog;
import cn.cassan.pm.util.ImageUtils;
import cn.cassan.pm.util.TDevice;
import cn.cassan.pm.widget.TouchImageView;

/**
 * 图片预览界面
 */
public class PhotosActivity extends BaseActivity {

    public static final String BUNDLE_KEY_IMAGES = "bundle_key_images";
    private TouchImageView mTouchImageView;
    private ProgressBar mProgressBar;
    private ImageView mOption;
    private AsyncTask<Void, Void, Bitmap> task;
    private String mImageUrl;

    public static void showImagePreview(Context context, String imageUrl) {
        Intent intent = new Intent(context, PhotosActivity.class);
        intent.putExtra(BUNDLE_KEY_IMAGES, imageUrl);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        super.onCreate(savedInstanceState);
        setContentView(cn.cassan.pm.R.layout.activity_photo_browse);
        mImageUrl = getIntent().getStringExtra(BUNDLE_KEY_IMAGES);

        mTouchImageView = (TouchImageView) findViewById(cn.cassan.pm.R.id.photoview);

        mTouchImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mProgressBar = (ProgressBar) findViewById(cn.cassan.pm.R.id.pb_loading);

        mOption = (ImageView) findViewById(cn.cassan.pm.R.id.iv_more);
        mOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOptionMenu();
            }
        });

        loadImage(mTouchImageView, mImageUrl);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }

    private void showOptionMenu() {
        final ImageMenuDialog dialog = new ImageMenuDialog(this);
        dialog.show();
        dialog.setCancelable(true);
        dialog.setOnMenuClickListener(new ImageMenuDialog.OnMenuClickListener() {
            @Override
            public void onClick(TextView menuItem) {
                if (menuItem.getId() == cn.cassan.pm.R.id.menu1) {
                    saveImg();
                } else if (menuItem.getId() == cn.cassan.pm.R.id.menu2) {
//                    sendTweet();
                } else if (menuItem.getId() == cn.cassan.pm.R.id.menu3) {
                    copyUrl();
                }
                dialog.dismiss();
            }
        });
    }

    /**
     * 复制链接
     */
    private void copyUrl() {
        TDevice.copyTextToBoard(mImageUrl);
        AppContext.showToastShort("已复制到剪贴板");
    }

    /**
     * 发送到动弹
     */
//    private void sendTweet() {
//        Bundle bundle = new Bundle();
//        bundle.putString(TweetPubActivity.REPOST_IMAGE_KEY, mImageUrl);
//        UIHelper.showTweetActivity(this, TweetPubActivity.ACTION_TYPE_REPOST, bundle);
//        finish();
//    }

    /**
     * 保存图片
     */
    private void saveImg() {
        final String filePath = AppConfig.DEFAULT_SAVE_IMAGE_PATH
                + getFileName(mImageUrl);

        Drawable drawable = mTouchImageView.getDrawable();
        if (drawable != null && drawable instanceof BitmapDrawable) {
            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
            try {
                ImageUtils.saveImageToSD(this, filePath, bitmap, 100);
                AppContext.showToastShort(getString(cn.cassan.pm.R.string.tip_save_image_suc));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //Core.getKJBitmap().saveImage(this, mImageUrl, filePath);
    }

    private String getFileName(String imgUrl) {
        int index = imgUrl.lastIndexOf('/') + 1;
        if (index == -1) {
            return System.currentTimeMillis() + ".jpeg";
        }
        return imgUrl.substring(index);
    }

    /**
     * 加载缩略图
     */
    private void loadImage(final ImageView mHeaderImageView, final String imageUrl) {
        Glide.with(this).load(imageUrl).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                mHeaderImageView.setImageBitmap(resource);
                mProgressBar.setVisibility(View.GONE);
                mHeaderImageView.setVisibility(View.VISIBLE);
                mOption.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {

    }

}
