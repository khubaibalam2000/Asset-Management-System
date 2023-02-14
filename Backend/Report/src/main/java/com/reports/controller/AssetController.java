package com.reports.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.reports.service.*;
import com.reports.model.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
  
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.*;

@RestController
@RequestMapping("/assets")
public class AssetController {
	
	@Autowired
	private AssetService assetService;
	
	private PdfDocument makeFile(List<Asset> assets, String fileName) throws FileNotFoundException {
	    PdfDocument pdfDoc = new PdfDocument(new PdfWriter(fileName));
	    Document doc = new Document(pdfDoc);
	    Table table = new Table(8);
	    
	    table.addCell(new Cell().add(new Paragraph("Id")));
	    table.addCell(new Cell().add(new Paragraph("Type")));
	    table.addCell(new Cell().add(new Paragraph("Location")));
	    table.addCell(new Cell().add(new Paragraph("Threat")));
	    table.addCell(new Cell().add(new Paragraph("Level")));
	    table.addCell(new Cell().add(new Paragraph("Current Defense")));
	    table.addCell(new Cell().add(new Paragraph("Proposed Defense")));
	    table.addCell(new Cell().add(new Paragraph("Created at")));
	    for (Asset i : assets) {
	    	if (i.getAssetId() != null) table.addCell(new Cell().add(new Paragraph(i.getAssetId().toString())));
		    if (i.getType() != null) table.addCell(new Cell().add(new Paragraph(i.getType())));
		    if (i.getLocation() != null) table.addCell(new Cell().add(new Paragraph(i.getLocation())));
		    if (i.getThreat() != null) table.addCell(new Cell().add(new Paragraph(i.getThreat())));
		    if (i.getLevel() != null) table.addCell(new Cell().add(new Paragraph(i.getLevel().toString())));
		    if (i.getCurrentdefense() != null) table.addCell(new Cell().add(new Paragraph(i.getCurrentdefense())));
		    if (i.getProposeddefense() != null) table.addCell(new Cell().add(new Paragraph(i.getProposeddefense())));
		    if (i.getCreatedat() != null) table.addCell(new Cell().add(new Paragraph(i.getCreatedat().toString())));
	    }
	
	    doc.add(table);
	    doc.close();
	    return pdfDoc;
	}
	
	private static byte[] convertFileContentToByteArray(File file) throws IOException {
        FileInputStream fl = new FileInputStream(file);
        byte[] arr = new byte[(int)file.length()];
        fl.read(arr);
        fl.close();
        return arr;
    }
	
	private ResponseEntity<byte[]> setResponseContent(byte[] contents, String filename) {
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_PDF);
	    headers.setContentDispositionFormData(filename, filename);
	    headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
	    ResponseEntity<byte[]> response = new ResponseEntity<>(contents, headers, HttpStatus.OK);
	    return response;
	}
	
	@GetMapping
    public ResponseEntity<byte[]> getAllAssets() throws FileNotFoundException, IOException {
		makeFile(assetService.getAllAssets(), "AllAssets.pdf");
	    return setResponseContent(convertFileContentToByteArray(new File("AllAssets.pdf")), "AllAssetsRet.pdf");
    }
	
	@GetMapping("/{assetId}")
    public Asset getAssetById(@PathVariable Integer assetId)  {
		return assetService.getAssetById(assetId);
    }

	@GetMapping("/type/{type}")
    public ResponseEntity<byte[]> getAssetByType(@PathVariable String type) throws FileNotFoundException, IOException {
		makeFile(assetService.getAssetByType(type), "TypeAssets.pdf");
	    return setResponseContent(convertFileContentToByteArray(new File("TypeAssets.pdf")), "TypeAssetsRet.pdf");
    }
	
	@GetMapping("/location/{location}")
    public ResponseEntity<byte[]> getAssetByLocation(@PathVariable String location) throws FileNotFoundException, IOException {
		makeFile(assetService.getAssetByLocation(location), "LocationAssets.pdf");
	    return setResponseContent(convertFileContentToByteArray(new File("LocationAssets.pdf")), "LocationAssetsRet.pdf");
    }
	
	@GetMapping("/threat/{threat}")
    public ResponseEntity<byte[]> getAssetByThreat(@PathVariable String threat) throws FileNotFoundException, IOException {
		makeFile(assetService.getAssetByThreat(threat), "ThreatAssets.pdf");
	    return setResponseContent(convertFileContentToByteArray(new File("ThreatAssets.pdf")), "ThreatAssetsRet.pdf");
    }
	
	@GetMapping("/level/{lowLevel}/{highLevel}")
    public ResponseEntity<byte[]> getAssetByLevel(@PathVariable Integer lowLevel, @PathVariable Integer highLevel) throws FileNotFoundException, IOException {
		makeFile(assetService.getAssetByLevel(lowLevel, highLevel), "LevelAssets.pdf");
	    return setResponseContent(convertFileContentToByteArray(new File("LevelAssets.pdf")), "LevelAssetsRet.pdf");
    }
}
