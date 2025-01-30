package com.nqlo.ch.mkt.service.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nqlo.ch.mkt.service.entities.ErrorResponse;
import com.nqlo.ch.mkt.service.entities.Receipt;
import com.nqlo.ch.mkt.service.exceptions.ResourceNotFoundException;
import com.nqlo.ch.mkt.service.services.ReceiptService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/receipts")
public class ReceiptController {

    @Autowired
    private ReceiptService receiptService;

        @Operation(summary = "Get Receipts List")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Receipts retrieved successfully",
                content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Receipt.class))}),
        @ApiResponse(responseCode = "500", description = "Internal server error",
                content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}),})
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Receipt>> getAllReceipts() {
        List<Receipt> receipts = receiptService.getReceipts();
        return ResponseEntity.ok(receipts);
    }

@Operation(summary = "Get Receipt by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Receipt retrieved successfully", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = Receipt.class))}),
        @ApiResponse(responseCode = "404", description = "Receipt not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))})
    @GetMapping("/{id}")
    public ResponseEntity<Receipt> getReceiptById(@PathVariable Long id) {
        Receipt receipt = receiptService.findById(id);
        return ResponseEntity.ok(receipt);
    }

    @Operation(summary = "Get Receipt by SaleId")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Receipt retrieved successfully", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = Receipt.class))}),
        @ApiResponse(responseCode = "404", description = "Receipt not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))})
    @GetMapping("/sale/{saleId}")
    public ResponseEntity<Receipt> getReceiptBySaleId(@PathVariable Long saleId) {
        Receipt receipt = receiptService.findBySaleId(saleId);
        if (receipt == null){
            throw new ResourceNotFoundException("Recipt with sale id: " + saleId + " couldn't be found");
        }
        return ResponseEntity.ok(receipt);
    }

    @Operation(summary = "Create a Receipt")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Receipt created successfully", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = Receipt.class))}),
        @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))})
    @PostMapping(value = "/{saleId}/export", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> createReceipt(@Valid @PathVariable Long saleId) {
        Receipt receipt = receiptService.save(saleId);
        return ResponseEntity.status(HttpStatus.CREATED).body(receipt);
    }
}