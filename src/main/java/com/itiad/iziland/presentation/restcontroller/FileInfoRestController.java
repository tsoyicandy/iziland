package com.itiad.iziland.presentation.restcontroller;

import com.itiad.iziland.models.entities.Bien;
import com.itiad.iziland.models.entities.FileInfo;
import com.itiad.iziland.repositories.BienRepository;
import com.itiad.iziland.repositories.FileInfoRepository;
import com.itiad.iziland.security.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/")
public class FileInfoRestController {


    @Autowired
    BienRepository bienRepository;

    @Autowired
    private FileInfoRepository fileInfoRepository;

    public Bien bienById(Long id){
        Bien bien = bienRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException(("ce bien n'existe pas !!"))) ;
        return bien;
    }
    String OUT_PATHI = "D:\\stage\\iziland\\uploads\\images\\";
    String OUT_PATHD = "D:\\stage\\iziland\\uploads\\documents\\";

    @PostMapping(value="/uploadimages/{idbien}")
    public ResponseEntity<String> uploadFile(@PathVariable("idbien") Long idbien , @RequestParam("file") List<MultipartFile> files) {
        String message = "";


        if (files == null || files.size()==0){
            message="Aucune image ajoutée";
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(message);
        }else {

            try {
                for (MultipartFile mf : files) {
                    byte[] bytes = mf.getBytes();
                    Path path = Paths.get(OUT_PATHI + mf.getOriginalFilename());
                    Files.write(path, bytes);
                    FileInfo fileInfo = new FileInfo(mf.getOriginalFilename(), path.toString());
                    fileInfo.setBien(bienById(idbien));
                    fileInfo.setTypeDocument("Image");
                    fileInfoRepository.save(fileInfo);
                }
                message = "Uploaded files successfully ";

            } catch (IOException e) {
                message = "Could not uploads the files " + e.getMessage();
                return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
            }
            return ResponseEntity.status(HttpStatus.OK).body(message);
        }

        }


    @GetMapping("/imagesFiles")
    public ResponseEntity<List<FileInfo>> getListFiles() {
        try {
            List<FileInfo> fileInfos = fileInfoRepository.findByTypeDocument("Image");
            if (fileInfos.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }else{
                return new ResponseEntity<>(fileInfos, HttpStatus.OK);
            }
        }catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/files/{filename}")
    @ResponseBody
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename) throws MalformedURLException {
        Path file = Paths.get(OUT_PATHI).resolve(filename);
        Resource resource = new UrlResource(file.toUri());
        if (resource.exists() || resource.isReadable()) {
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"").body(resource);

        } else {
            throw new RuntimeException("Could not read the file!");
        }

         }

    @DeleteMapping("/files/{filename}")
    public ResponseEntity<Map<String, Boolean>> deleteBien(@PathVariable String filename) throws IOException {
        FileInfo fileInfo = fileInfoRepository.findByName(filename).orElseThrow(()-> new ResourceNotFoundException(("ce fichier n'existe pas !!"))) ;
        fileInfoRepository.delete(fileInfo);
        Files.delete(Paths.get(OUT_PATHI+filename));
        Map<String, Boolean> reponse = new HashMap<>();
        reponse.put("supprime", Boolean.TRUE);
        return ResponseEntity.ok(reponse);
    }




}
