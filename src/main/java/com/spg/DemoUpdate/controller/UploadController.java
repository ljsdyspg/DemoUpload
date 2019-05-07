package com.spg.DemoUpdate.controller;

import com.spg.DemoUpdate.utils.OSSClientUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author SPG
 * @version 1.0
 * @time 2019-05-07-15:30
 */
@Controller
public class UploadController {


    private String UPLOAD_FOLDER = "files/";

    @GetMapping("/")
    public String index() {
        return "upload";
    }

    @PostMapping("/upload")
    public String singleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            return "redirect:/uploadStatus";
        }

        try {
            // 获取上传文件的文件名
            String fileName= OSSClientUtil.createFileName(file.getOriginalFilename());
            // 上传该文件
            OSSClientUtil.uploadFile(file.getInputStream(),fileName);

            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOAD_FOLDER + file.getOriginalFilename());
            //如果没有files文件夹，则创建
            if (!Files.isWritable(path)) {
                Files.createDirectories(Paths.get(UPLOAD_FOLDER));
            }
            //文件写入指定路径
            Files.write(path, bytes);

            redirectAttributes.addFlashAttribute("message",   file.getOriginalFilename() + " 已成功上传");

            String files_info = OSSClientUtil.showFiles();
            redirectAttributes.addFlashAttribute("files_info",   files_info);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:/uploadStatus";
    }

    @GetMapping("/uploadStatus")
    public String uploadStatus() {
        return "uploadStatus";
    }
}
