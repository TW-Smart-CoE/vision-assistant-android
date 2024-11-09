package com.thoughtworks.visionassistant.opencvkit.analyzers

import android.content.Context
import com.thoughtworks.visionassistant.core.Logger
import com.thoughtworks.visionassistant.opencvkit.R
import org.opencv.android.CameraBridgeViewBase
import org.opencv.core.Core
import org.opencv.core.Mat
import org.opencv.core.MatOfByte
import org.opencv.core.Rect
import org.opencv.core.Scalar
import org.opencv.core.Size
import org.opencv.imgproc.Imgproc
import org.opencv.objdetect.FaceDetectorYN
import java.io.IOException


data class FaceRect(val x: Int, val y: Int, val width: Int, val height: Int)

class FaceDetector(
    context: Context,
    private val flipVertical: Boolean,
    private val frameSkip: Int,
    private val faceDetectorListener: FaceDetectorListener,
    private val logger: Logger,
) : CameraBridgeViewBase.CvCameraViewListener2 {
    interface FaceDetectorListener {
        fun onFaceDetected(imageWidth: Int, imageHeight: Int, faceRectangles: List<FaceRect>)
    }

    private var mRgba: Mat = Mat()
    private var mBgr: Mat = Mat()
    private var mBgrScaled: Mat = Mat()
    private var mInputSize: Size? = null
    private val mScale: Float = 2.0f
    private var mModelBuffer: MatOfByte
    private var mConfigBuffer: MatOfByte = MatOfByte()
    private var mFaceDetector: FaceDetectorYN
    private var mFaces: Mat = Mat()
    private var frameAlreadySkipped = 0

    init {
        val buffer: ByteArray
        try {
            // load cascade file from application resources
            val inputStream = context.resources.openRawResource(R.raw.face_detection_yunet_2023mar)
            val size = inputStream.available()
            buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
            logger.error("[$TAG] Failed to load ONNX model from resources! Exception thrown: $e")
            throw RuntimeException("Failed to load ONNX model from resources!")
        }

        mModelBuffer = MatOfByte(*buffer)
        mFaceDetector =
            FaceDetectorYN.create("onnx", mModelBuffer, mConfigBuffer, Size(320.0, 320.0))
                ?: throw RuntimeException("Failed to create FaceDetectorYN!")
    }

    override fun onCameraViewStarted(width: Int, height: Int) {
        mRgba = Mat()
        mBgr = Mat()
        mBgrScaled = Mat()
        mFaces = Mat()
    }

    override fun onCameraViewStopped() {
        mRgba.release()
        mBgr.release()
        mBgrScaled.release()
        mFaces.release()
    }

    override fun onCameraFrame(inputFrame: CameraBridgeViewBase.CvCameraViewFrame?): Mat {
        mRgba = inputFrame!!.rgba()

        if (flipVertical) {
            Core.flip(mRgba, mRgba, 0)
        }

        if (frameAlreadySkipped < frameSkip) {
            frameAlreadySkipped++
            return mRgba
        } else {
            frameAlreadySkipped = 0
        }

        val inputSize = Size(
            Math.round(mRgba.cols() / mScale).toDouble(),
            Math.round(mRgba.rows() / mScale).toDouble()
        )
        if (mInputSize == null || mInputSize != inputSize) {
            mInputSize = inputSize
            mFaceDetector.inputSize = mInputSize
        }

        Imgproc.cvtColor(mRgba, mBgr, Imgproc.COLOR_RGBA2BGR)
        Imgproc.resize(mBgr, mBgrScaled, mInputSize)

        mFaceDetector.detect(mBgrScaled, mFaces)
        visualize(mRgba, mFaces)

        return mRgba
    }

    private fun visualize(rgba: Mat?, faces: Mat) {
        val thickness = 2
        val faceData = FloatArray(faces.cols() * faces.channels())
        val faceRectangles = mutableListOf<FaceRect>()

        for (i in 0 until faces.rows()) {
            faces[i, 0, faceData]

            logger.debug(
                "[$TAG] imageSize (${rgba?.size()}), Detected face (" + faceData[0] + ", " + faceData[1] + ", " +
                        faceData[2] + ", " + faceData[3] + ")"
            )

            // Create a Rect object and add it to the list
            faceRectangles.add(
                FaceRect(
                    Math.round(mScale * faceData[0]),
                    Math.round(mScale * faceData[1]),
                    Math.round(mScale * faceData[2]),
                    Math.round(mScale * faceData[3]),
                )
            )

            // Draw bounding box
            Imgproc.rectangle(
                rgba, Rect(
                    Math.round(mScale * faceData[0]), Math.round(mScale * faceData[1]),
                    Math.round(mScale * faceData[2]), Math.round(mScale * faceData[3])
                ),
                BOX_COLOR, thickness
            )
            // Draw landmarks
//            Imgproc.circle(
//                rgba,
//                Point(
//                    Math.round(mScale * faceData[4]).toDouble(),
//                    Math.round(mScale * faceData[5]).toDouble()
//                ),
//                2,
//                RIGHT_EYE_COLOR,
//                thickness
//            )
//            Imgproc.circle(
//                rgba,
//                Point(
//                    Math.round(mScale * faceData[6]).toDouble(),
//                    Math.round(mScale * faceData[7]).toDouble()
//                ),
//                2,
//                LEFT_EYE_COLOR,
//                thickness
//            )
//            Imgproc.circle(
//                rgba,
//                Point(
//                    Math.round(mScale * faceData[8]).toDouble(),
//                    Math.round(mScale * faceData[9]).toDouble()
//                ),
//                2,
//                NOSE_TIP_COLOR,
//                thickness
//            )
//            Imgproc.circle(
//                rgba,
//                Point(
//                    Math.round(mScale * faceData[10]).toDouble(),
//                    Math.round(mScale * faceData[11]).toDouble()
//                ),
//                2,
//                MOUTH_RIGHT_COLOR,
//                thickness
//            )
//            Imgproc.circle(
//                rgba,
//                Point(
//                    Math.round(mScale * faceData[12]).toDouble(),
//                    Math.round(mScale * faceData[13]).toDouble()
//                ),
//                2,
//                MOUTH_LEFT_COLOR,
//                thickness
//            )
        }

        faceDetectorListener?.onFaceDetected(rgba!!.width(), rgba.height(), faceRectangles)
    }

    companion object {
        private const val TAG = "FaceDetector"

        private val BOX_COLOR: Scalar = Scalar(0.0, 255.0, 0.0)
        private val RIGHT_EYE_COLOR: Scalar = Scalar(255.0, 0.0, 0.0)
        private val LEFT_EYE_COLOR: Scalar = Scalar(0.0, 0.0, 255.0)
        private val NOSE_TIP_COLOR: Scalar = Scalar(0.0, 255.0, 0.0)
        private val MOUTH_RIGHT_COLOR: Scalar = Scalar(255.0, 0.0, 255.0)
        private val MOUTH_LEFT_COLOR: Scalar = Scalar(0.0, 255.0, 255.0)
    }
}