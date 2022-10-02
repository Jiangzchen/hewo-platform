package org.hewo.file.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.hewo.infrastructure.controller.BaseRestController;
import org.hewo.infrastructure.model.response.ModelResponse;
import org.hewo.infrastructure.utils.FileUtil;
import org.hewo.infrastructure.utils.StringUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping(value = "file")
@Api(value = "文件服务")
public class FileController extends BaseRestController {

    @Value("${file.server:false}")
    private Boolean fileServer;

    @PostMapping({"upload"})
    @ApiOperation("上传文件")
    public ModelResponse<String> uploadFile(@RequestParam("file") MultipartFile file) throws Exception {
        String fileExtName = FileUtil.getFileExtName(file.getOriginalFilename());
        String fileKey = StringUtil.getUUID();
        String fileName = fileKey + "." + fileExtName;
        fileName = file.getOriginalFilename();
        return null;
    }
}
