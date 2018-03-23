package com.group18.cs446.spacequest.game.vfx;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.DrawFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Picture;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;

import java.util.LinkedList;
import java.util.List;

public class CanvasComponent {
    private List<Canvas> canvases;
    
    public CanvasComponent() {
        canvases = new LinkedList<>();
    }
    
    public void addCanvas(Canvas canvas){
        this.canvases.add(canvas);
    }

    public void save(){
        for (Canvas c : canvases) c.save();
    }
    public void restore(){
        for (Canvas c : canvases) c.restore();
    }

    public int getWidth(){
        for (Canvas c : canvases){
            return c.getWidth(); // just return first one
        }
        return 0;
    }

    public int getHeight(){
        for (Canvas c : canvases){
            return c.getHeight(); // just return first one
        }
        return 0;
    }

    public void translate(float dx, float dy) {
        for(Canvas c : canvases) c.translate(dx, dy);
    }

    
    public void scale(float sx, float sy) {
        for(Canvas c : canvases) c.scale(sx, sy);
    }

    
    public void rotate(float degrees) {
        for(Canvas c : canvases) c.rotate(degrees);
    }

    
    public void skew(float sx, float sy) {
        for(Canvas c : canvases) c.skew(sx, sy);
    }

    
    public void concat(@Nullable Matrix matrix) {
        for(Canvas c : canvases) c.concat(matrix);
    }

    
    public void setMatrix(@Nullable Matrix matrix) {
        for(Canvas c : canvases) c.setMatrix(matrix);
    }

    
    public void getMatrix(@NonNull Matrix ctm) {
        for(Canvas c : canvases) c.getMatrix(ctm);
    }

    
    public void setDrawFilter(@Nullable DrawFilter filter) {
        for(Canvas c : canvases) c.setDrawFilter(filter);
    }

    
    public void drawPicture(@NonNull Picture picture) {
        for(Canvas c : canvases) c.drawPicture(picture);
    }

    
    public void drawPicture(@NonNull Picture picture, @NonNull RectF dst) {
        for(Canvas c : canvases) c.drawPicture(picture, dst);
    }

    
    public void drawPicture(@NonNull Picture picture, @NonNull Rect dst) {
        for(Canvas c : canvases) c.drawPicture(picture, dst);
    }

    
    public void drawArc(@NonNull RectF oval, float startAngle, float sweepAngle, boolean useCenter, @NonNull Paint paint) {
        for(Canvas c : canvases) c.drawArc(oval, startAngle, sweepAngle, useCenter, paint);
    }
    
    public void drawARGB(int a, int r, int g, int b) {
        for(Canvas c : canvases) c.drawARGB(a, r, g, b);
    }

    
    public void drawBitmap(@NonNull Bitmap bitmap, float left, float top, @Nullable Paint paint) {
        for(Canvas c : canvases) c.drawBitmap(bitmap, left, top, paint);
    }

    
    public void drawBitmap(@NonNull Bitmap bitmap, @Nullable Rect src, @NonNull RectF dst, @Nullable Paint paint) {
        for(Canvas c : canvases) c.drawBitmap(bitmap, src, dst, paint);
    }

    
    public void drawBitmap(@NonNull Bitmap bitmap, @Nullable Rect src, @NonNull Rect dst, @Nullable Paint paint) {
        for(Canvas c : canvases) c.drawBitmap(bitmap, src, dst, paint);
    }

    
    public void drawBitmap(@NonNull int[] colors, int offset, int stride, float x, float y, int width, int height, boolean hasAlpha, @Nullable Paint paint) {
        for(Canvas c : canvases) c.drawBitmap(colors, offset, stride, x, y, width, height, hasAlpha, paint);
    }

    
    public void drawBitmap(@NonNull int[] colors, int offset, int stride, int x, int y, int width, int height, boolean hasAlpha, @Nullable Paint paint) {
        for(Canvas c : canvases) c.drawBitmap(colors, offset, stride, x, y, width, height, hasAlpha, paint);
    }

    
    public void drawBitmap(@NonNull Bitmap bitmap, @NonNull Matrix matrix, @Nullable Paint paint) {
        for(Canvas c : canvases) c.drawBitmap(bitmap, matrix, paint);
    }

    
    public void drawBitmapMesh(@NonNull Bitmap bitmap, int meshWidth, int meshHeight, @NonNull float[] verts, int vertOffset, @Nullable int[] colors, int colorOffset, @Nullable Paint paint) {
        for(Canvas c : canvases) c.drawBitmapMesh(bitmap, meshWidth, meshHeight, verts, vertOffset, colors, colorOffset, paint);
    }

    
    public void drawCircle(float cx, float cy, float radius, @NonNull Paint paint) {
        for(Canvas c : canvases) c.drawCircle(cx, cy, radius, paint);
    }

    
    public void drawColor(int color) {
        for(Canvas c : canvases) c.drawColor(color);
    }

    
    public void drawColor(int color, @NonNull PorterDuff.Mode mode) {
        for(Canvas c : canvases) c.drawColor(color, mode);
    }

    
    public void drawLine(float startX, float startY, float stopX, float stopY, @NonNull Paint paint) {
        for(Canvas c : canvases) c.drawLine(startX, startY, stopX, stopY, paint);
    }

    
    public void drawLines(@NonNull float[] pts, int offset, int count, @NonNull Paint paint) {
        for(Canvas c : canvases) c.drawLines(pts, offset, count, paint);
    }

    
    public void drawLines(@NonNull float[] pts, @NonNull Paint paint) {
        for(Canvas c : canvases) c.drawLines(pts, paint);
    }

    
    public void drawOval(@NonNull RectF oval, @NonNull Paint paint) {
        for(Canvas c : canvases) c.drawOval(oval, paint);
    }

    
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void drawOval(float left, float top, float right, float bottom, @NonNull Paint paint) {
        for(Canvas c : canvases) c.drawOval(left, top, right, bottom, paint);
    }

    
    public void drawPaint(@NonNull Paint paint) {
        for(Canvas c : canvases) c.drawPaint(paint);
    }

    
    public void drawPath(@NonNull Path path, @NonNull Paint paint) {
        for(Canvas c : canvases) c.drawPath(path, paint);
    }

    
    public void drawPoint(float x, float y, @NonNull Paint paint) {
        for(Canvas c : canvases) c.drawPoint(x, y, paint);
    }

    
    public void drawPoints(float[] pts, int offset, int count, @NonNull Paint paint) {
        for(Canvas c : canvases) c.drawPoints(pts, offset, count, paint);
    }

    
    public void drawPoints(@NonNull float[] pts, @NonNull Paint paint) {
        for(Canvas c : canvases) c.drawPoints(pts, paint);
    }

    
    public void drawPosText(@NonNull char[] text, int index, int count, @NonNull float[] pos, @NonNull Paint paint) {
        for(Canvas c : canvases) c.drawPosText(text, index, count, pos, paint);
    }

    
    public void drawPosText(@NonNull String text, @NonNull float[] pos, @NonNull Paint paint) {
        for(Canvas c : canvases) c.drawPosText(text, pos, paint);
    }

    
    public void drawRect(@NonNull RectF rect, @NonNull Paint paint) {
        for(Canvas c : canvases) c.drawRect(rect, paint);
    }

    
    public void drawRect(@NonNull Rect r, @NonNull Paint paint) {
        for(Canvas c : canvases) c.drawRect(r, paint);
    }

    
    public void drawRect(float left, float top, float right, float bottom, @NonNull Paint paint) {
        for(Canvas c : canvases) c.drawRect(left, top, right, bottom, paint);
    }

    
    public void drawRGB(int r, int g, int b) {
        for(Canvas c : canvases) c.drawRGB(r, g, b);
    }

    
    public void drawRoundRect(@NonNull RectF rect, float rx, float ry, @NonNull Paint paint) {
        for(Canvas c : canvases) c.drawRoundRect(rect, rx, ry, paint);
    }

    
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void drawRoundRect(float left, float top, float right, float bottom, float rx, float ry, @NonNull Paint paint) {
        for(Canvas c : canvases) c.drawRoundRect(left, top, right, bottom, rx, ry, paint);
    }

    
    public void drawText(@NonNull char[] text, int index, int count, float x, float y, @NonNull Paint paint) {
        for(Canvas c : canvases) c.drawText(text, index, count, x, y, paint);
    }

    
    public void drawText(@NonNull String text, float x, float y, @NonNull Paint paint) {
        for(Canvas c : canvases) c.drawText(text, x, y, paint);
    }

    
    public void drawText(@NonNull String text, int start, int end, float x, float y, @NonNull Paint paint) {
        for(Canvas c : canvases) c.drawText(text, start, end, x, y, paint);
    }

    
    public void drawText(@NonNull CharSequence text, int start, int end, float x, float y, @NonNull Paint paint) {
        for(Canvas c : canvases) c.drawText(text, start, end, x, y, paint);
    }

    
    public void drawTextOnPath(@NonNull char[] text, int index, int count, @NonNull Path path, float hOffset, float vOffset, @NonNull Paint paint) {
        for(Canvas c : canvases) c.drawTextOnPath(text, index, count, path, hOffset, vOffset, paint);
    }

    
    public void drawTextOnPath(@NonNull String text, @NonNull Path path, float hOffset, float vOffset, @NonNull Paint paint) {
        for(Canvas c : canvases) c.drawTextOnPath(text, path, hOffset, vOffset, paint);
    }

    
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void drawTextRun(@NonNull char[] text, int index, int count, int contextIndex, int contextCount, float x, float y, boolean isRtl, @NonNull Paint paint) {
        for(Canvas c : canvases) c.drawTextRun(text, index, count, contextIndex, contextCount, x, y, isRtl, paint);
    }

    
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void drawTextRun(@NonNull CharSequence text, int start, int end, int contextStart, int contextEnd, float x, float y, boolean isRtl, @NonNull Paint paint) {
        for(Canvas c : canvases) c.drawTextRun(text, start, end, contextStart, contextEnd, x, y, isRtl, paint);
    }

    
    public void drawVertices(@NonNull Canvas.VertexMode mode, int vertexCount, @NonNull float[] verts, int vertOffset, @Nullable float[] texs, int texOffset, @Nullable int[] colors, int colorOffset, @Nullable short[] indices, int indexOffset, int indexCount, @NonNull Paint paint) {
        for(Canvas c : canvases) c.drawVertices(mode, vertexCount, verts, vertOffset, texs, texOffset, colors, colorOffset, indices, indexOffset, indexCount, paint);
    }

    public void rotate(int i, int i1, int i2) {
        for (Canvas c : canvases) c.rotate(i, i1, i2);
    }

    public Rect getClipBounds(){
        for (Canvas c : canvases) return c.getClipBounds();
        return null;
    }
}
