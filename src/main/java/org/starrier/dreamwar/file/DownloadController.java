package org.starrier.dreamwar.file;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * @author Starrier
 * @date 2018/12/9.
 */
@RestController
@RequestMapping(value = "/download")
public class DownloadController {



    /**
     * <p>Download Image</p>
     * 1.String split according to the file name passed in to get the image you want to download
     * 2.
     * @param image
     * @return ResponseEntity {@link org.springframework.http.ResponseEntity;}
     * */
    @GetMapping(value = "/img")
    public ResponseEntity<Object> downLoad(@RequestParam("image") String image, HttpServletResponse response){

        String filename = image + ".jpg";
        String filePath = "F:/test" ;
        File file = new File(filePath + "/" + filename);
        if(file.exists()){
            response.setContentType("application/force-download");
            response.setHeader("Content-Disposition", "attachment;fileName=" + filename);

            byte[] buffer = new byte[1024];
            FileInputStream fis = null;
            BufferedInputStream bis = null;

            OutputStream os = null;
            try {
                os = response.getOutputStream();
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                int i = bis.read(buffer);
                while(i != -1){
                    os.write(buffer);
                    i = bis.read(buffer);
                }

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            try {
                bis.close();
                fis.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return new ResponseEntity<>("The image requested has been downloaded successfully!", HttpStatus.OK);
    }

}
