package vn.xdeuhug.currency_converter.utils

import android.annotation.SuppressLint
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import vn.xdeuhug.currency_converter.R

/**
 * @Author: NGUYEN XUAN DIEU
 * @Date: 22 / 10 / 2024
 */
object PhotoUtils {
    // Đang dùng
    fun getLinkPhoto(photo: String?): String {
        return String.format("%s%s", "", photo)
    }

    fun loadPhotoImageAvatar(url: String, view: ImageView) {
        if (url.contains("/")) {
            Glide.with(view.context.applicationContext).asBitmap().load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL).apply(
                    RequestOptions().placeholder(R.drawable.vn).centerCrop().error(R.drawable.vn)
                ).transform(MultiTransformation(CircleCrop())).into(view)
        } else {
            Glide.with(view.context.applicationContext).asBitmap().load(
                String.format(
                    "%s%s", "", url
                )
            ).diskCacheStrategy(DiskCacheStrategy.ALL).apply(
                RequestOptions().placeholder(R.drawable.vn).centerCrop().transform(
                    MultiTransformation(CircleCrop())
                ).error(R.drawable.vn)
            ).into(view)
        }
    }

    fun loadPhotoImageNormal(url: String, view: ImageView) {
        if (url.contains("/")) {
            Glide.with(view.context.applicationContext).asBitmap().load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL).apply(
                    RequestOptions().placeholder(R.drawable.vn).error(R.drawable.vn)
                ).into(view)
        } else {
            Glide.with(view.context.applicationContext).asBitmap().load(
                String.format(
                    "%s%s", "", url
                )
            ).diskCacheStrategy(DiskCacheStrategy.ALL).apply(
                RequestOptions().placeholder(R.drawable.vn).error(R.drawable.vn)
            ).into(view)
        }
    }

    //    fun loadPhotoImageAvatarNormal(url: String, view: ImageView) {
//        if (url.contains("/")) {
//            Glide.with(view.context.applicationContext).asBitmap().load(url)
//                .diskCacheStrategy(DiskCacheStrategy.ALL).apply(
//                    RequestOptions().placeholder(R.drawable.ic_cast_default).error(R.drawable.ic_cast_default)
//                ).into(view)
//        } else {
//            Glide.with(view.context.applicationContext).asBitmap().load(
//                String.format(
//                    "%s%s", "", url
//                )
//            ).diskCacheStrategy(DiskCacheStrategy.ALL).apply(
//                RequestOptions().placeholder(R.drawable.ic_cast_default).error(R.drawable.ic_cast_default)
//            ).into(view)
//        }
//    }
//
    fun loadPhotoRound(url: String, view: ImageView) {
        if (url.contains("/")) {
            Glide.with(view.context.applicationContext).asBitmap().load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL).apply(
                    RequestOptions().placeholder(R.drawable.vn).error(R.drawable.vn)
                ).centerCrop().transform(
                    MultiTransformation(
                        RoundedCorners(
                            view.context.resources.getDimension(R.dimen.dp_8).toInt()
                        )
                    )
                ).into(view)
        } else {
            Glide.with(view.context.applicationContext).asBitmap().load(
                String.format(
                    "%s%s", "", url
                )
            ).diskCacheStrategy(DiskCacheStrategy.ALL).centerCrop().transform(
                MultiTransformation(
                    RoundedCorners(
                        view.context.resources.getDimension(R.dimen.dp_8).toInt()
                    )
                )
            ).apply(
                RequestOptions().centerCrop().transform(
                    MultiTransformation(
                        RoundedCorners(
                            view.context.resources.getDimension(R.dimen.dp_8).toInt()
                        )
                    )
                ).placeholder(R.drawable.vn).error(R.drawable.vn)
            ).into(view)
        }
    }

    @SuppressLint("DiscouragedApi")
    fun loadImageLocal(imagePath: String, imageView: ImageView) {
        val context = imageView.context
        val resourceId = context.resources.getIdentifier(imagePath, null, context.packageName)
        Glide.with(context)
            .load(resourceId)
            .placeholder(R.drawable.vn).error(R.drawable.vn)
            .into(imageView)

    }
}