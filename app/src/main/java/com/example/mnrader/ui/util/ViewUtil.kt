package com.example.mnrader.ui.util

import android.graphics.BitmapShader
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.core.graphics.scale
import androidx.core.graphics.createBitmap
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Shader.TileMode


inline fun Modifier.noRippleClickable(
    crossinline onClick: () -> Unit
): Modifier = composed {
    clickable(
        indication = null,
        interactionSource = remember { MutableInteractionSource() }
    ) {
        onClick()
    }
}


/**
 * 원본 [src] 비트맵을 원형으로 자르고, 테두리를 추가해서 반환합니다.
 *
 * @param src         원본 Bitmap
 * @param diameterPx  출력할 원형 지름 (픽셀 단위)
 * @param borderWidthPx 테두리 두께 (픽셀 단위)
 * @param borderColor 테두리 색상 (ARGB)
 * @return 테두리가 적용된 원형 Bitmap
 */
fun createCircularBitmapWithBorder(
    src: Bitmap,
    diameterPx: Int,
    borderWidthPx: Int,
    borderColor: Int
): Bitmap {
    // 1) 먼저 원본을 diameterPx×diameterPx 크기로 스케일
    val scaled = src.scale(diameterPx, diameterPx)

    // 2) 결과를 담을 ARGB_8888 포맷의 빈 Bitmap 생성
    val output = createBitmap(diameterPx, diameterPx)
    val canvas = Canvas(output)

    // 3) 원본 이미지를 그릴 때 쓸 BitmapShader
    val shader = BitmapShader(scaled, TileMode.CLAMP, TileMode.CLAMP)
    val paintBitmap = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        isAntiAlias = true
        this.shader = shader
    }

    // 4) 테두리를 그릴 파인트
    val paintBorder = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        color = borderColor
        strokeWidth = borderWidthPx.toFloat()
        isAntiAlias = true
    }

    // 5) 실제로 캔버스에 그리기
    val radius = diameterPx / 2f
    // 5-1) 먼저 원본을 원형으로 마스킹해서 그림
    canvas.drawCircle(radius, radius, radius - borderWidthPx, paintBitmap)
    // 5-2) 그 위에 외곽 원형을 테두리 두께만큼만 남겨두고 그림
    canvas.drawCircle(radius, radius, radius - borderWidthPx / 2f, paintBorder)

    return output
}