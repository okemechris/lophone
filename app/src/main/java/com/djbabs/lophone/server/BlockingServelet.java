package com.djbabs.lophone.server;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.Html;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BlockingServelet extends HttpServlet {



    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws  IOException {

        ArrayList<String> list =getImagesPath(JettyServer.activity);
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().print("<html><head><title>Index</title>");
        response.getWriter().print("<style>#gallery {\n" +
                "  max-width: 960px;\n" +
                "  margin: 0 auto; \n" +
                "  text-align: center; \n" +
                "  padding-bottom: 360px;}" +
                "html {\n" +
                "  background: linear-gradient(#444, #666);\n" +
                "  min-height: 100%;\n" +
                "  font-family: \"Century Gothic\", sans-serif;\n" +
                "} h1 {\n" +
                "    color: rgb(249,229,89);\n" +
                "    font-size: 16px *3;\n" +
                "    font-weight: $font-weight;\n" +
                "    padding: 50px 40px 0px;\n" +
                "    text-transform: uppercase;\n" +
                "  }\n" +
                "  p {\n" +
                "    color: rgb(255,255,255);\n" +
                "    padding: 0 40px;\n" +
                "  }" +
                ".thumbnail {\n" +
                "\t\tfloat: left;\n" +
                "\t\tposition: relative;\n" +
                "\t\twidth: 15%;\n" +
                "\t\tpadding-bottom: 15%;\n" +
                "\t\tmargin: 0.83%;\n" +
                "\t\toverflow: hidden;\t\t\n" +
                "\t\t&:hover {\n" +
                "\t\t-webkit-box-shadow: 5px 5px 50px 0px rgba(0,0,0,0.75);\n" +
                "\t\t-moz-box-shadow: 5px 5px 50px 0px rgba(0,0,0,0.75);\n" +
                "\t\tbox-shadow: 5px 5px 50px 0px rgba(0,0,0,0.75);\n" +
                "\t\t}" +
                ".img-container{\n" +
                "\t  position: absolute;\n" +
                "\t  width: 100%;\n" +
                "\t  height: 100%;}" +
                "img {\n" +
                "      width: auto;\n" +
                "  -webkit-transform: translate(-50%,-50%);\n" +
                "      -ms-transform: translate(-50%,-50%);\n" +
                "          transform: translate(-50%,-50%);\n" +
                "    }" +
                "&:hover .img-caption{\n" +
                "\t\ttop: 0;\n" +
                "\t\tleft: 0;\n" +
                "\t\t.btn-trans {\n" +
                "\t\t\tbackground:  rgba(255,255,255,0.4);\n" +
                "\t\t\tborder: 1px solid rgb(249,229,89);\n" +
                "\t\t\t}\n" +

                "</style></head>");
        response.getWriter().print("<body><section id=\"gallery\">\n" +
                "<h1>Perfect square image gallery</h1>\n" +
                "  <p>A responsive image gallery using only CSS with a centered button on the hover and centered and cropped thumbnails.</p>");
        for (String i : list) {
            response.getWriter().print("<div class=\"thumbnail\">\n" +
                    "\t    <div class=\"img-container\"><a href=\"\"><img  style=\"width:200px;height:200px\" src=\""+i+"\" alt=\"...\" />\n" +
                    "\t\t<div class=\"img-caption table\"><span class=\"table-cell\"><button class=\"btn btn-p btn-trans\" role=\"button\">Download</button></span></div></a></div>\n" +
                    "\t</div>");
        }

        response.getWriter().print("</section></body></html>");
    }

    public static ArrayList<String> getImagesPath(Activity activity) {
        Uri uri;
        ArrayList<String> listOfAllImages = new ArrayList<String>();
        Cursor cursor;
        int column_index_data;
        String PathOfImage = null;
        uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String[] projection = { MediaStore.MediaColumns.DATA,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME };

        cursor = activity.getContentResolver().query(uri, projection, null,
                null, null);

        column_index_data = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);

        while (cursor.moveToNext()) {
            PathOfImage = cursor.getString(column_index_data);
            String[] list = PathOfImage.split("0/");
            String name = list[list.length-1];
            listOfAllImages.add(name);
        }
        cursor.close();
        return listOfAllImages;
    }


}
