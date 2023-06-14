package com.sklerbidi.handsealstrainer;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

import java.io.IOException;

public class Alphabet1Camera extends Activity implements CameraBridgeViewBase.CvCameraViewListener2{
    private static final String TAG="MainActivity";

    private Mat mRgba;
    private Mat mGray;
    private CameraBridgeViewBase mOpenCvCameraView;
    private Alphabet1DetectorClass Alphabet1DetectorClass;
    private BaseLoaderCallback mLoaderCallback =new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status){
                case LoaderCallbackInterface
                        .SUCCESS:{
                    Log.i(TAG,"OpenCv Is loaded");
                    mOpenCvCameraView.enableView();
                }
                default:
                {
                    super.onManagerConnected(status);

                }
                break;
            }
        }
    };

    public Alphabet1Camera(){
        Log.i(TAG,"Instantiated new "+this.getClass());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String letter = null;

        if(intent != null){
            letter = intent.getStringExtra("letter");
        }

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        int MY_PERMISSIONS_REQUEST_CAMERA=0;
        if (ContextCompat.checkSelfPermission(Alphabet1Camera.this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(Alphabet1Camera.this, new String[] {Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
        }

        setContentView(R.layout.activity_camera);

        mOpenCvCameraView=(CameraBridgeViewBase) findViewById(R.id.frame_Surface);
        mOpenCvCameraView.setVisibility(SurfaceView.VISIBLE);
        mOpenCvCameraView.setCvCameraViewListener(this);
        try{
            if (letter != null) {
                switch (letter.toLowerCase()) {
                    case "a":
                        Alphabet1DetectorClass = new Alphabet1DetectorClass(getAssets(),"hand_model.tflite","custom_label.txt",300,"A.tflite",96, letter);
                        break;
                    case "b":
                        Alphabet1DetectorClass = new Alphabet1DetectorClass(getAssets(),"hand_model.tflite","custom_label.txt",300,"B.tflite",96, letter);
                        break;
                    case "c":
                        Alphabet1DetectorClass = new Alphabet1DetectorClass(getAssets(),"hand_model.tflite","custom_label.txt",300,"C.tflite",96, letter);
                        break;
                    case "d":
                        Alphabet1DetectorClass = new Alphabet1DetectorClass(getAssets(),"hand_model.tflite","custom_label.txt",300,"D.tflite",96, letter);
                        break;
                    case "e":
                        Alphabet1DetectorClass = new Alphabet1DetectorClass(getAssets(),"hand_model.tflite","custom_label.txt",300,"E.tflite",96, letter);
                        break;
                    case "f":
                        Alphabet1DetectorClass = new Alphabet1DetectorClass(getAssets(),"hand_model.tflite","custom_label.txt",300,"F.tflite",96, letter);
                        break;
                    case "g":
                        Alphabet1DetectorClass = new Alphabet1DetectorClass(getAssets(),"hand_model.tflite","custom_label.txt",300,"G.tflite",96, letter);
                        break;
                    case "h":
                        Alphabet1DetectorClass = new Alphabet1DetectorClass(getAssets(),"hand_model.tflite","custom_label.txt",300,"H.tflite",96, letter);
                        break;
                    case "i":
                        Alphabet1DetectorClass = new Alphabet1DetectorClass(getAssets(),"hand_model.tflite","custom_label.txt",300,"I.tflite",96, letter);
                        break;
                    case "j":
                        Alphabet1DetectorClass = new Alphabet1DetectorClass(getAssets(),"hand_model.tflite","custom_label.txt",300,"J.tflite",96, letter);
                        break;
                    case "k":
                        Alphabet1DetectorClass = new Alphabet1DetectorClass(getAssets(),"hand_model.tflite","custom_label.txt",300,"K.tflite",96, letter);
                        break;
                    case "l":
                        Alphabet1DetectorClass = new Alphabet1DetectorClass(getAssets(),"hand_model.tflite","custom_label.txt",300,"L.tflite",96, letter);
                        break;
                    case "m":
                        Alphabet1DetectorClass = new Alphabet1DetectorClass(getAssets(),"hand_model.tflite","custom_label.txt",300,"M.tflite",96, letter);
                        break;
                    case "n":
                        Alphabet1DetectorClass = new Alphabet1DetectorClass(getAssets(),"hand_model.tflite","custom_label.txt",300,"N.tflite",96, letter);
                        break;
                    case "o":
                        Alphabet1DetectorClass = new Alphabet1DetectorClass(getAssets(),"hand_model.tflite","custom_label.txt",300,"O.tflite",96, letter);
                        break;
                    case "p":
                        Alphabet1DetectorClass = new Alphabet1DetectorClass(getAssets(),"hand_model.tflite","custom_label.txt",300,"P.tflite",96, letter);
                        break;
                    case "q":
                        Alphabet1DetectorClass = new Alphabet1DetectorClass(getAssets(),"hand_model.tflite","custom_label.txt",300,"Q.tflite",96, letter);
                        break;
                    case "r":
                        Alphabet1DetectorClass = new Alphabet1DetectorClass(getAssets(),"hand_model.tflite","custom_label.txt",300,"R.tflite",96, letter);
                        break;
                    case "s":
                        Alphabet1DetectorClass = new Alphabet1DetectorClass(getAssets(),"hand_model.tflite","custom_label.txt",300,"S.tflite",96, letter);
                        break;
                    case "t":
                        Alphabet1DetectorClass = new Alphabet1DetectorClass(getAssets(),"hand_model.tflite","custom_label.txt",300,"T.tflite",96, letter);
                        break;
                    case "u":
                        Alphabet1DetectorClass = new Alphabet1DetectorClass(getAssets(),"hand_model.tflite","custom_label.txt",300,"U.tflite",96, letter);
                        break;
                    case "v":
                        Alphabet1DetectorClass = new Alphabet1DetectorClass(getAssets(),"hand_model.tflite","custom_label.txt",300,"V.tflite",96, letter);
                        break;
                    case "w":
                        Alphabet1DetectorClass = new Alphabet1DetectorClass(getAssets(),"hand_model.tflite","custom_label.txt",300,"W.tflite",96, letter);
                        break;
                    case "x":
                        Alphabet1DetectorClass = new Alphabet1DetectorClass(getAssets(),"hand_model.tflite","custom_label.txt",300,"X.tflite",96, letter);
                        break;
                    case "y":
                        Alphabet1DetectorClass = new Alphabet1DetectorClass(getAssets(),"hand_model.tflite","custom_label.txt",300,"Y.tflite",96, letter);
                        break;
                    default:
                        // Handle the case where the letter is not recognized
                        break;
                }
            } else {
                Alphabet1DetectorClass = new Alphabet1DetectorClass(getAssets(),"hand_model.tflite","custom_label.txt",300,"sign_language_model.tflite",96, null);
            }
            Log.d("MainActivity","Model is successfully loaded");
        }
        catch (IOException e){
            Log.d("MainActivity","Getting some error");
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (OpenCVLoader.initDebug()){
            Log.d(TAG,"Opencv initialization is done");
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
        else{
            //if not loaded
            Log.d(TAG,"Opencv is not loaded. try again");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION,this,mLoaderCallback);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mOpenCvCameraView !=null){
            mOpenCvCameraView.disableView();
        }
    }

    public void onDestroy(){
        super.onDestroy();
        if(mOpenCvCameraView !=null){
            mOpenCvCameraView.disableView();
        }

    }

    public void onCameraViewStarted(int width ,int height){
        mRgba=new Mat(height,width, CvType.CV_8UC4);
        mGray =new Mat(height,width,CvType.CV_8UC1);
    }
    public void onCameraViewStopped(){
        mRgba.release();
    }
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame){
        mRgba=inputFrame.rgba();
        mGray=inputFrame.gray();

        Mat out=new Mat();
        out= Alphabet1DetectorClass.recognizeImage(mRgba);

        return out;
    }

}