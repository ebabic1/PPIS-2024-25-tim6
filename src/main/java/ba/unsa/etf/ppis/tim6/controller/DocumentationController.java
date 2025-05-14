package ba.unsa.etf.ppis.tim6.controller;

import ba.unsa.etf.ppis.tim6.dto.DocumentationDTO;
import ba.unsa.etf.ppis.tim6.mapper.DocumentationMapper;
import ba.unsa.etf.ppis.tim6.model.Documentation;
import ba.unsa.etf.ppis.tim6.repository.DocumentationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/documentations")
@RequiredArgsConstructor
public class DocumentationController {

    private final DocumentationRepository documentationRepository;
    private final DocumentationMapper documentationMapper;

    @GetMapping
    public ResponseEntity<List<DocumentationDTO>> getAllDocumentations() {
        List<Documentation> documentations = documentationRepository.findAll();
        List<DocumentationDTO> documentationDTOs = documentations.stream()
                .map(documentationMapper::documentationToDocumentationDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(documentationDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DocumentationDTO> getDocumentationById(@PathVariable Long id) {
        Optional<Documentation> documentation = documentationRepository.findById(id);
        return documentation.map(doc -> ResponseEntity.ok(documentationMapper.documentationToDocumentationDTO(doc)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<DocumentationDTO> createDocumentation(@RequestBody DocumentationDTO documentationDTO) {
        Documentation documentation = documentationMapper.documentationDTOToDocumentation(documentationDTO);
        Documentation savedDocumentation = documentationRepository.save(documentation);
        return ResponseEntity.ok(documentationMapper.documentationToDocumentationDTO(savedDocumentation));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DocumentationDTO> updateDocumentation(@PathVariable Long id, @RequestBody DocumentationDTO documentationDTO) {
        return documentationRepository.findById(id).map(existingDocumentation -> {
            existingDocumentation.setDocumentType(documentationDTO.getDocumentType());
            existingDocumentation.setContent(documentationDTO.getContent());
            Documentation updatedDocumentation = documentationRepository.save(existingDocumentation);
            return ResponseEntity.ok(documentationMapper.documentationToDocumentationDTO(updatedDocumentation));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDocumentation(@PathVariable Long id) {
        if (documentationRepository.existsById(id)) {
            documentationRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
