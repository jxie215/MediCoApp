package com.example.jaden.medicoapp.patientrecord.uploadimage;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jaden.medicoapp.R;

import static android.app.Activity.RESULT_OK;

/**
 * Created by snehalsutar on 2/18/16.
 */
public class FragmentUploadImage extends Fragment implements View.OnClickListener {

    private static final int IMAGE_PICK_REQUEST_CODE = 1;
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 2;
    TextView messageText;
    ImageView imageView;
    Button uploadButton;
    Button chooseFromGalleryButton, clickFromCameraButton;
    int serverResponseCode = 0;
    ProgressDialog dialog = null;
    Context mContext;
    Activity mActivity;
    String upLoadServerUri = null;


    /**********  File Path *************/
    final String uploadFilePath = Environment.getExternalStorageDirectory().toString();
    final String uploadFileName = "/download.jpg";
    Uri imageUri                      = null;

    /***********************************************************************************************
     * ON CREATE VIEW for fragment.
     */

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_upload_images, container, false);
        mContext=getActivity();
        mActivity = getActivity();

//        uploadButton = (Button)rootView.findViewById(R.id.uploadButton);
//        messageText  = (TextView)rootView.findViewById(R.id.messageText);
        imageView = (ImageView) rootView.findViewById(R.id.imageViewShowImage);
//        messageText.setText("Uploading file path :- '"+uploadFilePath+uploadFileName+"'");


        chooseFromGalleryButton = (Button) rootView.findViewById(R.id.button_choose_from_gallery);
        clickFromCameraButton = (Button) rootView.findViewById(R.id.button_click_from_camera);
        chooseFromGalleryButton.setOnClickListener(this);
        clickFromCameraButton.setOnClickListener(this);
        /************* Php script path ****************/
        upLoadServerUri = "http://rjtmobile.com/ansari/image_store.php";

//        uploadButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                dialog = ProgressDialog.show(mContext, "", "Uploading file...", true);
//
//                new Thread(new Runnable() {
//                    public void run() {
//                        mActivity.runOnUiThread(new Runnable() {
//                            public void run() {
//                                messageText.setText("uploading started.....");
//                            }
//                        });
//
//                        uploadFile(uploadFilePath + "" + uploadFileName);
//
//                    }
//                }).start();
//            }
//        });


        return rootView;
    }

//    public int uploadFile(String sourceFileUri) {
//
//
//        String fileName = sourceFileUri;
//
//        HttpURLConnection conn = null;
//        DataOutputStream dos = null;
//        String lineEnd = "\r\n";
//        String twoHyphens = "--";
//        String boundary = "*****";
//        int bytesRead, bytesAvailable, bufferSize;
//        byte[] buffer;
//        int maxBufferSize = 1 * 1024 * 1024;
//        File sourceFile = new File(sourceFileUri);
//
//        if (!sourceFile.isFile()) {
//
//            dialog.dismiss();
//
//            Log.e("uploadFile", "Source File not exist :"
//                    + uploadFilePath + "" + uploadFileName);
//
//            mActivity.runOnUiThread(new Runnable() {
//                public void run() {
//                    messageText.setText("Source File not exist :"
//                            + uploadFilePath + "" + uploadFileName);
//                }
//            });
//
//            return 0;
//
//        }
//        else
//        {
//            try {
//
//                // open a URL connection to the Servlet
//                FileInputStream fileInputStream = new FileInputStream(sourceFile);
//                URL url = new URL(upLoadServerUri);
//
//                // Open a HTTP  connection to  the URL
//                conn = (HttpURLConnection) url.openConnection();
//                conn.setDoInput(true); // Allow Inputs
//                conn.setDoOutput(true); // Allow Outputs
//                conn.setUseCaches(false); // Don't use a Cached Copy
//                conn.setRequestMethod("POST");
//                conn.setRequestProperty("Connection", "Keep-Alive");
//                conn.setRequestProperty("ENCTYPE", "multipart/form-data");
//                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
//                conn.setRequestProperty("uploaded_file", fileName);
//
//                dos = new DataOutputStream(conn.getOutputStream());
//
//                dos.writeBytes(twoHyphens + boundary + lineEnd);
//                dos.writeBytes("Content-Disposition: form-data; name=uploaded_file;filename=" + fileName + "" + lineEnd);
//
//                dos.writeBytes(lineEnd);
//
//                // create a buffer of  maximum size
//                bytesAvailable = fileInputStream.available();
//
//                bufferSize = Math.min(bytesAvailable, maxBufferSize);
//                buffer = new byte[bufferSize];
//
//                // read file and write it into form...
//                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
//
//                while (bytesRead > 0) {
//
//                    dos.write(buffer, 0, bufferSize);
//                    bytesAvailable = fileInputStream.available();
//                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
//                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);
//
//                }
//
//                // send multipart form data necesssary after file data...
//                dos.writeBytes(lineEnd);
//                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
//
//                // Responses from the server (code and message)
//                serverResponseCode = conn.getResponseCode();
//                String serverResponseMessage = conn.getResponseMessage();
//
//                Log.i("uploadFile", "HTTP Response is : "
//                        + serverResponseMessage + ": " + serverResponseCode);
//
//                if(serverResponseCode == 200){
//
//                    mActivity.runOnUiThread(new Runnable() {
//                        public void run() {
//
//                            String msg = "File Upload Completed.\n\n See uploaded file here : \n\n"
//                                    + " http://www.androidexample.com/media/uploads/"
//                                    + "download.jpg";
//
//                            messageText.setText(msg);
//                            Toast.makeText(mContext, "File Upload Complete.",
//                                    Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                }
//
//                //close the streams //
//                fileInputStream.close();
//                dos.flush();
//                dos.close();
//
//            } catch (MalformedURLException ex) {
//
//                dialog.dismiss();
//                ex.printStackTrace();
//
//                mActivity.runOnUiThread(new Runnable() {
//                    public void run() {
//                        messageText.setText("MalformedURLException Exception : check script url.");
//                        Toast.makeText(mContext, "MalformedURLException",
//                                Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//                Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
//            } catch (Exception e) {
//
//                dialog.dismiss();
//                e.printStackTrace();
//
//                mActivity.runOnUiThread(new Runnable() {
//                    public void run() {
//                        messageText.setText("Got Exception : see logcat ");
//                        Toast.makeText(mContext, "Got Exception : see logcat ",
//                                Toast.LENGTH_SHORT).show();
//                    }
//                });
//                Log.e("Upload Exception", "Exception : " + e.getMessage(), e);
//            }
//            dialog.dismiss();
//            return serverResponseCode;
//
//        } // End else block
//    }

    /***********************************************************************************************
     * On Click Listeners for Choose Image From Gallery and Click Image from Camera.
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_choose_from_gallery:
                chooseImageFromGallery();
                break;
            case R.id.button_click_from_camera:
                chooseImageFromCamera();
                break;
        }
    }

    private void chooseImageFromGallery() {

        Intent i = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(i, IMAGE_PICK_REQUEST_CODE);
    }

    private void chooseImageFromCamera() {

//
//        /*************************** Camera Intent Start ************************/
//
//        // Define the file-name to save photo taken by Camera activity
//
        String fileName = "Camera_Example.jpg";
//
//        // Create parameters for Intent with filename
//
        ContentValues values = new ContentValues();

        values.put(MediaStore.Images.Media.TITLE, fileName);

        values.put(MediaStore.Images.Media.DESCRIPTION,"Image capture by camera");
//
//        // imageUri is the current activity attribute, define and save it for later usage
//
        imageUri = mContext.getContentResolver().insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
//
//        /**** EXTERNAL_CONTENT_URI : style URI for the "primary" external storage volume. ****/
//
//
//        // Standard Intent action that can be sent to have the camera
//        // application capture an image and return it.
//
        Intent intent = new Intent( MediaStore.ACTION_IMAGE_CAPTURE );

        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);

        startActivityForResult( intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);

//          for storing pics in a specific location
//        Intent imageIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//
//        //folder stuff
//        File imagesFolder = new File(Environment.getExternalStorageDirectory(), "MyImages");
//        imagesFolder.mkdirs();
//
//        File image = new File(imagesFolder, "QR_" + timeStamp + ".png");
//        Uri uriSavedImage = Uri.fromFile(image);
//
//        imageIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);
//        startActivityForResult(imageIntent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        /*************************** Camera Intent End ************************/

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_PICK_REQUEST_CODE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            Log.i("Abdul",String.valueOf(selectedImage));
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);

            cursor.close();

            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));

        }
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getActivity().getContentResolver().query(imageUri,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);

            cursor.close();

            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
        }


    }
}
