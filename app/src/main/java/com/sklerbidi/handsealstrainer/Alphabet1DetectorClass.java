package com.sklerbidi.handsealstrainer;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.util.Log;

import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.tensorflow.lite.Interpreter;
import org.tensorflow.lite.gpu.GpuDelegate;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class    Alphabet1DetectorClass {
    private Interpreter interpreter;
    private Interpreter interpreter2;
    // store all label in array
    private List<String> labelList;
    private int INPUT_SIZE;
    private int PIXEL_SIZE = 3; // for RGB
    private int IMAGE_MEAN = 0;
    private float IMAGE_STD = 255.0f;
    // use to initialize gpu in app
    private GpuDelegate gpuDelegate;
    private int height = 0;
    private int width = 0;
    private int Classification_Input_Size = 0;

    private String letterToDetect = null;

    Alphabet1DetectorClass(AssetManager assetManager, String modelPath, String labelPath, int inputSize, String classification_model, int classification_input_size, String letter) throws IOException {
        letterToDetect = letter;
        INPUT_SIZE = inputSize;
        Classification_Input_Size = classification_input_size;
        Interpreter.Options options = new Interpreter.Options();
        gpuDelegate = new GpuDelegate();
        options.addDelegate(gpuDelegate);
        options.setNumThreads(4);
        interpreter = new Interpreter(loadModelFile(assetManager, modelPath), options);
        labelList = loadLabelList(assetManager, labelPath);
        Interpreter.Options option2 = new Interpreter.Options();
        option2.setNumThreads(2);
        interpreter2 = new Interpreter(loadModelFile(assetManager, classification_model), option2);
    }

    private List<String> loadLabelList(AssetManager assetManager, String labelPath) throws IOException {
        List<String> labelList = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(assetManager.open(labelPath)));
        String line;
        while ((line = reader.readLine()) != null) {
            labelList.add(line);
        }
        reader.close();
        return labelList;
    }

    private ByteBuffer loadModelFile(AssetManager assetManager, String modelPath) throws IOException {
        AssetFileDescriptor fileDescriptor = assetManager.openFd(modelPath);
        FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel = inputStream.getChannel();
        long startOffset = fileDescriptor.getStartOffset();
        long declaredLength = fileDescriptor.getDeclaredLength();

        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
    }


    public Mat recognizeImage(Mat mat_image) {

        Mat rotated_mat_image = new Mat();

        Mat a = mat_image.t();
        Core.flip(a, rotated_mat_image, 1);

        a.release();

        Bitmap bitmap = null;
        bitmap = Bitmap.createBitmap(rotated_mat_image.cols(), rotated_mat_image.rows(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(rotated_mat_image, bitmap);
        height = bitmap.getHeight();
        width = bitmap.getWidth();
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, INPUT_SIZE, INPUT_SIZE, false);

        ByteBuffer byteBuffer = convertBitmapToByteBuffer(scaledBitmap);
        Object[] input = new Object[1];
        input[0] = byteBuffer;

        Map<Integer, Object> output_map = new TreeMap<>();

        float[][][] boxes = new float[1][10][4];
        float[][] scores = new float[1][10];
        float[][] classes = new float[1][10];

        output_map.put(0, boxes);
        output_map.put(1, classes);
        output_map.put(2, scores);

        interpreter.runForMultipleInputsOutputs(input, output_map);

        Object value = output_map.get(0);
        Object Object_class = output_map.get(1);
        Object score = output_map.get(2);


        for (int i = 0; i < 10; i++) {
            float class_value = (float) Array.get(Array.get(Object_class, 0), i);
            float score_value = (float) Array.get(Array.get(score, 0), i);



            if (score_value > 0.5) {
                Object box1 = Array.get(Array.get(value, 0), i);

                float y1 = (float) Array.get(box1, 0) * height;
                float x1 = (float) Array.get(box1, 1) * width;
                float y2 = (float) Array.get(box1, 2) * height;
                float x2 = (float) Array.get(box1, 3) * width;


                if (y1 < 0) {
                    y1 = 0;
                }
                if (x1 < 0) {
                    x1 = 0;
                }
                if (x2 > width) {
                    x2 = width;
                }
                if (y2 > height) {
                    y2 = height;
                }

                float w1 = x2 - x1;
                float h1 = y2 - y1;

                Rect cropped_roi = new Rect((int) x1, (int) y1, (int) w1, (int) h1);
                Mat cropped = new Mat(rotated_mat_image, cropped_roi).clone();
                Bitmap bitmap1 = null;
                bitmap1 = Bitmap.createBitmap(cropped.cols(), cropped.rows(), Bitmap.Config.ARGB_8888);
                Utils.matToBitmap(cropped, bitmap1);
                Bitmap scaledBitmap1 = Bitmap.createScaledBitmap(bitmap1, Classification_Input_Size, Classification_Input_Size, false);
                ByteBuffer byteBuffer1 = convertBitmapToByteBuffer1(scaledBitmap1);

                float[][] output_class_value=new float[1][1];

                interpreter2.run(byteBuffer1,output_class_value);

                Log.d("objectDetection", "output_class_value:  "+output_class_value);

                String sign_val=get_alphabets(output_class_value[0][0], letterToDetect);

                // draw rectangle in Original frame //  starting point    // ending point of box  // color of box       thickness
                Imgproc.rectangle(rotated_mat_image, new Point(x1, y1), new Point(x2, y2), new Scalar(0, 255, 0, 255), 2);
                Imgproc.putText(rotated_mat_image,""+sign_val,new Point(x1+10,y1+40),2,1.5,new Scalar(255, 255, 255, 255),2);

            }

        }
        Mat b = rotated_mat_image.t();
        Core.flip(b, mat_image, 0);
        b.release();
        return mat_image;
    }

    private String get_alphabets(float sig_v, String letterToGet) {

        String val = "";

        if(letterToGet != null){
            switch (letterToGet) {
                case "a":
                    if (sig_v >= -0.5f && sig_v < 0.5f) {
                        val = "A";
                    }
                    break;
                case "b":
                    if (sig_v >= -0.5f && sig_v < 0.5f) {
                        val = "B";
                    }
                    break;
                case "c":
                    if (sig_v >= -0.5f && sig_v < 0.5f) {
                        val = "C";
                    }
                    break;
                case "d":
                    if (sig_v >= -0.5f && sig_v < 0.5f) {
                        val = "D";
                    }
                    break;
                case "e":
                    if (sig_v >= -0.5f && sig_v < 0.5f) {
                        val = "E";
                    }
                    break;
                case "f":
                    if (sig_v >= -0.5f && sig_v < 0.5f) {
                        val = "F";
                    }
                    break;
                case "g":
                    if (sig_v >= -0.5f && sig_v < 0.5f) {
                        val = "G";
                    }
                    break;
                case "h":
                    if (sig_v >= -0.5f && sig_v < 0.5f) {
                        val = "H";
                    }
                    break;
                case "i":
                    if (sig_v >= -0.5f && sig_v < 0.5f) {
                        val = "I";
                    }
                    break;
                case "j":
                    if (sig_v >= -0.5f && sig_v < 0.5f) {
                        val = "J";
                    }
                    break;
                case "k":
                    if (sig_v >= -0.5f && sig_v < 0.5f) {
                        val = "K";
                    }
                    break;
                case "l":
                    if (sig_v >= -0.5f && sig_v < 0.5f) {
                        val = "L";
                    }
                    break;
                case "m":
                    if (sig_v >= -0.5f && sig_v < 0.5f) {
                        val = "M";
                    }
                    break;
                case "n":
                    if (sig_v >= -0.5f && sig_v < 0.5f) {
                        val = "N";
                    }
                    break;
                case "o":
                    if (sig_v >= -0.5f && sig_v < 0.5f) {
                        val = "O";
                    }
                    break;
                case "p":
                    if (sig_v >= -0.5f && sig_v < 0.5f) {
                        val = "P";
                    }
                    break;
                case "q":
                    if (sig_v >= -0.5f && sig_v < 0.5f) {
                        val = "Q";
                    }
                    break;
                case "r":
                    if (sig_v >= -0.5f && sig_v < 0.5f) {
                        val = "R";
                    }
                    break;
                case "s":
                    if (sig_v >= -0.5f && sig_v < 0.5f) {
                        val = "S";
                    }
                    break;
                case "t":
                    if (sig_v >= -0.5f && sig_v < 0.5f) {
                        val = "T";
                    }
                    break;
                case "u":
                    if (sig_v >= -0.5f && sig_v < 0.5f) {
                        val = "U";
                    }
                    break;
                case "v":
                    if (sig_v >= -0.5f && sig_v < 0.5f) {
                        val = "V";
                    }
                    break;
                case "w":
                    if (sig_v >= -0.5f && sig_v < 0.5f) {
                        val = "W";
                    }
                    break;
                case "x":
                    if (sig_v >= -0.5f && sig_v < 0.5f) {
                        val = "X";
                    }
                    break;
                case "y":
                    if (sig_v >= -0.5f && sig_v < 0.5f) {
                        val = "Y";
                    }
                    break;
            }
        }else{
            if (sig_v >= -0.5f && sig_v < 0.5f) {
                val = "A";
            } else if (sig_v >= 0.5f && sig_v < 1.5f) {
                val = "B";
            } else if (sig_v >= 1.5f && sig_v < 2.5f) {
                val = "C";
            } else if (sig_v >= 2.5f && sig_v < 3.5f) {
                val = "D";
            } else if (sig_v >= 3.5f && sig_v < 4.5f) {
                val = "E";
            } else if (sig_v >= 4.5f && sig_v < 5.5f) {
                val = "F";
            } else if (sig_v >= 5.5f && sig_v < 6.5f) {
                val = "G";
            } else if (sig_v >= 6.5f && sig_v < 7.5f) {
                val = "H";
            } else if (sig_v >= 7.5f && sig_v < 8.5f) {
                val = "I";
            } else if (sig_v >= 8.5f && sig_v < 9.5f) {
                val = "J";
            } else if (sig_v >= 9.5f && sig_v < 10.5f) {
                val = "K";
            } else if (sig_v >= 10.5f && sig_v < 11.5f) {
                val = "L";
            } else if (sig_v >= 11.5f && sig_v < 12.5f) {
                val = "M";
            } else if (sig_v >= 12.5f && sig_v < 13.5f) {
                val = "N";
            } else if (sig_v >= 13.5f && sig_v < 14.5f) {
                val = "O";
            } else if (sig_v >= 14.5f && sig_v < 15.5f) {
                val = "P";
            } else if (sig_v >= 15.5f && sig_v < 16.5f) {
                val = "Q";
            } else if (sig_v >= 16.5f && sig_v < 17.5f) {
                val = "R";
            } else if (sig_v >= 17.5f && sig_v < 18.5f) {
                val = "S";
            } else if (sig_v >= 18.5f && sig_v < 19.5f) {
                val = "T";
            } else if (sig_v >= 19.5f && sig_v < 20.5f) {
                val = "U";
            } else if (sig_v >= 20.5f && sig_v < 21.5f) {
                val = "V";
            } else if (sig_v >= 21.5f && sig_v < 22.5f) {
                val = "W";
            } else if (sig_v >= 22.5f && sig_v < 23.5f) {
                val = "X";
            } else if (sig_v >= 23.5f && sig_v < 24.5f) {
                val = "Y";
            }
        }

        return val;

    }


    private ByteBuffer convertBitmapToByteBuffer(Bitmap bitmap) {
        ByteBuffer byteBuffer;
        int quant = 1;
        int size_images = INPUT_SIZE;
        if (quant == 0) {
            byteBuffer = ByteBuffer.allocateDirect(1 * size_images * size_images * 3);
        } else {
            byteBuffer = ByteBuffer.allocateDirect(4 * 1 * size_images * size_images * 3);
        }
        byteBuffer.order(ByteOrder.nativeOrder());
        int[] intValues = new int[size_images * size_images];
        bitmap.getPixels(intValues, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
        int pixel = 0;

        for (int i = 0; i < size_images; ++i) {
            for (int j = 0; j < size_images; ++j) {
                final int val = intValues[pixel++];
                if (quant == 0) {
                    byteBuffer.put((byte) ((val >> 16) & 0xFF));
                    byteBuffer.put((byte) ((val >> 8) & 0xFF));
                    byteBuffer.put((byte) (val & 0xFF));
                } else {
                    // paste this
                    byteBuffer.putFloat((((val >> 16) & 0xFF)) / 255.0f);
                    byteBuffer.putFloat((((val >> 8) & 0xFF)) / 255.0f);
                    byteBuffer.putFloat((((val) & 0xFF)) / 255.0f);
                }
            }
        }
        return byteBuffer;
    }

    private ByteBuffer convertBitmapToByteBuffer1(Bitmap bitmap) {
        ByteBuffer byteBuffer;
        int quant = 1;
        int size_images = Classification_Input_Size;
        if (quant == 0) {
            byteBuffer = ByteBuffer.allocateDirect(1 * size_images * size_images * 3);
        } else {
            byteBuffer = ByteBuffer.allocateDirect(4 * 1 * size_images * size_images * 3);
        }
        byteBuffer.order(ByteOrder.nativeOrder());
        int[] intValues = new int[size_images * size_images];
        bitmap.getPixels(intValues, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
        int pixel = 0;

        // some error
        //now run
        for (int i = 0; i < size_images; ++i) {
            for (int j = 0; j < size_images; ++j) {
                final int val = intValues[pixel++];
                if (quant == 0) {
                    byteBuffer.put((byte) ((val >> 16) & 0xFF));
                    byteBuffer.put((byte) ((val >> 8) & 0xFF));
                    byteBuffer.put((byte) (val & 0xFF));
                } else {
                    byteBuffer.putFloat((((val >> 16) & 0xFF)));
                    byteBuffer.putFloat((((val >> 8) & 0xFF)));
                    byteBuffer.putFloat((((val) & 0xFF)));
                }
            }
        }
        return byteBuffer;
    }
}
