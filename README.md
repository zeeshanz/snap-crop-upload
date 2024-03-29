# Jixtē Snap-Crop-Upload Module

To make life easier for anyone wanting to easily take photos, crop, and upload to their server, I decided to write this module. You can integrate it into your app, and call it just using one line of code. This module will open a dialog box allowing you to take a photo, or select one from your library, and then it'll crop and upload to your server. There are a few good options to control the functionality of this module which I have explained below.

# Why I Wrote This Module
I wanted this functionality in my own apps, i.e. Foodisfy and JixtēLogs, but couldn't find any working code or examples anywhere. All the code I found online on various websites, blogs, answers, tutorials, etc. were obsolete, incomplete, or simply not working. Long story short, I couldn't find anything but found a lot of people asking for similar functionality. So I decided to write this code as an independent module, which could be intergrated into any app, and published it here for anyone who could benefit from it. This module targets API 32 and has backward compatibility all the way to API 16. Earlier I had support for earlier versions as well, going as back as Froyo, but given that Android has evolved significantely and it doesn't support older versions any longer, the oldest this module could support was API 16. I have tested this module on Samsung Galaxy S3 (JellyBean, SDK version 18), Nexus 6P (Naugat, SDK version 25) and Google Pixel 3 XL (Android 12 Snow Cone, SDK version updated as of March 2022) and it works perfect.

# What It Does
This module allows you to snap a photo, crop and upload to your server, just in one line of code. You can also select photos from your photo libraries. Refer to the sample to see it working.

1. Allows you to take a photo or select one from your photo library
2. Crops it
3. Stores the final image locally
4. Sends the final image to a remote server

# How To Use It
Import the module into your main project and add it as a dependency of your main project. Once done, all you have to do is one of the following:

- Just call a static method like this:

```java
init(context, imageFolder, cropWidth, cropHeight, imageType, url, overwrite)
```

- Or call it with a callback listener like the following example:

```java
SnapCropUpload mListener;
mListener = new SnapCropUpload();
  mListener.setListener(MainActivity.this, null, 800, 800, 0, null, true, new ISnapCropUploadListener() {
    @Override
    public void onReceiveImagePath(Uri imageUri) {
      Log.v(TAG, "Image path received: " + imageUri.getPath());
      // Do something with the image
    }
  });
}
});
```

- To delete a file:

```java
SnapCropUpload.delete(<filename>)
```

- To create a thumbnail:

```java
SnapCropUpload.makeThumbnail(Uri imageUri, int dimension)
```

# Options
1. `context` - the calling application's context
2. `imageFolder` - the subfolder under Pictures folder where the images will be stored. Leave it null for no subfolder. When null, the images will be stored in calling app's internal storage, which is the main files folder.
3. `cropWidth` - the crop width in pixels. A value of 0 will result in no crop.
4. `cropHeight` - the crop height in pixels. A value of 0 will result in no crop.
5. `imageType` - the image output type: 0 is JPG, 1 is PNG, 2 is BMP
6. `url` - the URL where to upload the image. Leave it null if you are not uploading.
7. `overwrite` -  set it to true if you want to over write the image everytime, otherwise set it to false.
8. `listener` - This callback will return the Uri of the final image. You might need this to display the image as a thumbnail.

Since Android API 24 it is not allowed to use `file://` outside the package domain, so I have updated the code to use `Uri` for image paths. You can easily extract path from a `Uri` using `myUri.getPath()`.

# The PHP Script
If you are using a PHP server to receive the files, you can use this this PHP which I am using on my server. Please make sure your uploads/ directory has the necessary permissions for the web user to write to it. I have this folder set with permissions 700.

```php
<?php
$path = "uploads/";
error_reporting(E_ALL);
ini_set('display_errors', '1');
//ini_set('error_log','/httpdocs/error_log');
echo "upload_max_filesize: " . ini_get ("upload_max_filesize"). "<br>";
echo "post_max_size: " . ini_get ("post_max_size"). "<br>";
echo "memory_limit: " . ini_get ("memory_limit"). "<br>";
echo "max_file_uploads: " . ini_get ("max_file_uploads"). "<br>";
echo "max_execution_time: " . ini_get ("max_execution_time"). "<br>";
echo "max_input_time: " . ini_get ("max_input_time"). "<br>";
echo "file_uploads: " . ini_get ("file_uploads"). "<br>";
echo "default_socket_timeout: " . ini_get ("default_socket_timeout"). "<br>";
echo "<br>";
if ($_FILES["file"]["error"] > 0)
  {
  echo "Error: " . $_FILES["file"]["error"] . "<br>";
  }
else
  {
  echo "Upload: " . $_FILES["file"]["name"] . "<br>";
  echo "Type: " . $_FILES["file"]["type"] . "<br>";
  echo "Size: " . ($_FILES["file"]["size"] / 1024) . " kB<br>";
  echo "Stored in: " . $_FILES["file"]["tmp_name"];
  move_uploaded_file($_FILES["file"]["tmp_name"], $path . '/' . $_FILES["file"]["name"]);
  }
?>
<html>
<body>

<form action="<?php echo $_SERVER['PHP_SELF']; ?>" method="post"
enctype="multipart/form-data">
<label for="file">Filename:</label>
<input type="file" name="file" id="file"><br>
<input type="submit" name="submit" value="Submit">
</form>

</body>
</html>
```
