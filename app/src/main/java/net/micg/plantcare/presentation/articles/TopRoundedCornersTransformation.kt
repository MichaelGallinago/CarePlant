package net.micg.plantcare.presentation.articles

import android.content.Context
import android.graphics.Bitmap
import android.util.TypedValue
import coil3.size.Size
import coil3.transform.RoundedCornersTransformation

class TopRoundedCornersTransformation(
    context: Context,
    private val radiusDp: Float
) : coil3.transform.Transformation() {

    override val cacheKey: String
        get() = "topRoundedCornersTransformation(radius=$radiusDp)"

    private val radiusPx = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        radiusDp,
        context.resources.displayMetrics
    )

    override suspend fun transform(input: Bitmap, size: Size) =
        RoundedCornersTransformation(radiusPx, radiusPx, 0f, 0f).transform(input, size)
}
