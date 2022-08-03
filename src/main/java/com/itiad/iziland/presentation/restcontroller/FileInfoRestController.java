package com.itiad.iziland.presentation.restcontroller;

import com.itiad.iziland.models.entities.Bien;
import com.itiad.iziland.models.entities.Etape;
import com.itiad.iziland.models.entities.FileInfo;
import com.itiad.iziland.repositories.BienRepository;
import com.itiad.iziland.repositories.EtapeRepository;
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

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/")
public class FileInfoRestController {


    @Autowired
    BienRepository bienRepository;

    @Autowired
    private FileInfoRepository fileInfoRepository;

    @Autowired
    private EtapeRepository etapeRepository;

    private Bien getbienById(Long id){
        return bienRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException(("ce bien n'existe pas !!")));
    }

    private Etape getEtapeById(Long id){
        return etapeRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException(("cette étape n'existe pas !!")));
    }

    private String OUT_PATHI = "D:\\stage\\iziland\\uploads\\images\\";
    private String OUT_PATHD = "D:\\stage\\iziland\\uploads\\documents\\";

    @PostMapping(value="/uploadimages/{idbien}")
    private ResponseEntity<String> uploadImages(@PathVariable("idbien") Long idbien, @RequestParam("file") List<MultipartFile> files) {
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
                    fileInfo.setBien(getbienById(idbien));
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

    @PostMapping(value="/uploaddocuments/{idetape}")
    public ResponseEntity<String> uploadDocuments(@PathVariable("idetape") Long idetape , @RequestParam("file") List<MultipartFile> files) {
        String message;


        if (files == null || files.size()==0){
            message="Aucun Document ajoutée";
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(message);
        }else {

            try {
                for (MultipartFile mf : files) {
                    byte[] bytes = mf.getBytes();
                    Path path = Paths.get(OUT_PATHD + mf.getOriginalFilename());
                    Files.write(path, bytes);
                    FileInfo fileInfo = new FileInfo(mf.getOriginalFilename(), path.toString());
                    fileInfo.setEtape(getEtapeById(idetape));
                    fileInfo.setTypeDocument("Document");
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
    public ResponseEntity<List<FileInfo>> getListImages() {
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

    @GetMapping("/documentsFiles")
    public ResponseEntity<List<FileInfo>> getListDocuments() {
        try {
            List<FileInfo> fileInfos = fileInfoRepository.findByTypeDocument("Document");
            if (fileInfos.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }else{
                return new ResponseEntity<>(fileInfos, HttpStatus.OK);
            }
        }catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getImagesBien/{id}")
    public ResponseEntity<List<FileInfo>> getImagesBien(@PathVariable("id") Long id){

        try {
            List<FileInfo> fileInfos = fileInfoRepository.findByBien(getbienById(id));
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
        Path file = Paths.get(OUT_PATHI+filename);
        Resource resource = new UrlResource(file.toUri());
        if (resource.exists() || resource.isReadable()) {
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"").body(resource);

        } else {
            throw new RuntimeException("Could not read the file!");
        }

         }

    @DeleteMapping("/files/{filename}")
    public ResponseEntity<Map<String, Boolean>> deletefile(@PathVariable String filename) throws IOException {
        FileInfo fileInfo = fileInfoRepository.findByName(filename).orElseThrow(()-> new ResourceNotFoundException(("ce fichier n'existe pas !!"))) ;
        fileInfoRepository.delete(fileInfo);
        Files.delete(Paths.get(OUT_PATHI+filename));
        Map<String, Boolean> reponse = new HashMap<>();
        reponse.put("supprime", Boolean.TRUE);
        return ResponseEntity.ok(reponse);
    }

    @GetMapping( "/getImage/{imageName}")
    public String getImage(@PathVariable("imageName") String imageName) throws Exception {

        Optional<FileInfo> retrievedImage = fileInfoRepository.findByName(imageName);
        return retrievedImage.get().getUrl();
    }




}
