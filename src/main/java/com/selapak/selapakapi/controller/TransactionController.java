package com.selapak.selapakapi.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.selapak.selapakapi.constant.AppPath;
import com.selapak.selapakapi.model.request.TransactionRequest;
import com.selapak.selapakapi.model.request.TransactionVerifyRequest;
import com.selapak.selapakapi.model.response.CommonResponse;
import com.selapak.selapakapi.model.response.CommonResponseWithPage;
import com.selapak.selapakapi.model.response.PagingResponse;
import com.selapak.selapakapi.model.response.TransactionResponse;
import com.selapak.selapakapi.service.TransactionService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(AppPath.TRANSACTION_PATH)
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<?> createTransaction(@RequestBody TransactionRequest request) {
        TransactionResponse transactionResponse = transactionService.create(request);
        CommonResponse<TransactionResponse> response = CommonResponse.<TransactionResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Create transaction successfully.")
                .data(transactionResponse)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping
    public ResponseEntity<?> getAllTransactions(@RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "5") Integer size) {
        Page<TransactionResponse> transactionResponses = transactionService.getAllWithDto(page - 1, size);
        PagingResponse pagingResponse = PagingResponse.builder()
                .currentPage(page)
                .totalPage(transactionResponses.getTotalPages())
                .size(size)
                .build();
        CommonResponseWithPage<Page<TransactionResponse>> response = CommonResponseWithPage.<Page<TransactionResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Get all transactions successfully.")
                .data(transactionResponses)
                .paging(pagingResponse)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping(AppPath.GET_BY_ID)
    public ResponseEntity<?> getTransactionById(@PathVariable String id) {
        TransactionResponse transactionResponse = transactionService.getByIdWithDto(id);
        CommonResponse<TransactionResponse> response = CommonResponse.<TransactionResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Get transaction successfully.")
                .data(transactionResponse)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping(AppPath.DELETE_BY_ID)
    public ResponseEntity<?> deleteTransactionById(@PathVariable String id) {
        transactionService.deleteById(id);
        CommonResponse<TransactionResponse> response = CommonResponse.<TransactionResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Delete transaction successfully.")
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping(AppPath.TRANSACTION_APPROVE)
    public ResponseEntity<?> approveTransaction(@PathVariable String id, @RequestBody TransactionVerifyRequest request) {
        TransactionResponse transactionResponse = transactionService.verifyApproveTransaction(id, request);
        CommonResponse<TransactionResponse> response = CommonResponse.<TransactionResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Approve transaction successfully.")
                .data(transactionResponse)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping(AppPath.TRANSACTION_REJECT)
    public ResponseEntity<?> rejectTransaction(@PathVariable String transactionId, @RequestBody TransactionVerifyRequest request) {
        TransactionResponse transactionResponse = transactionService.verifyRejectTransaction(transactionId, request);
        CommonResponse<TransactionResponse> response = CommonResponse.<TransactionResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Reject transaction successfully.")
                .data(transactionResponse)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping(AppPath.TRANSACTION_SURVEY)
    public ResponseEntity<?> surveyTransaction(@PathVariable String transactionId) {
        transactionService.doneSurveyLandTransaction(transactionId);
        CommonResponse<TransactionResponse> response = CommonResponse.<TransactionResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Done survey transaction successfully.")
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping(AppPath.TRANSACTION_ACCEPT)
    public ResponseEntity<?> acceptTransaction(@PathVariable String transactionId) {
        TransactionResponse transactionResponse = transactionService.acceptTransactionAfterSurveyByCustomer(transactionId);
        CommonResponse<TransactionResponse> response = CommonResponse.<TransactionResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Accept transaction successfully.")
                .data(transactionResponse)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping(AppPath.TRANSACTION_DECLINE)
    public ResponseEntity<?> declineTransaction(@PathVariable String transactionId) {
        TransactionResponse transactionResponse = transactionService.declineTransactionAfterSurveyByCustomer(transactionId);
        CommonResponse<TransactionResponse> response = CommonResponse.<TransactionResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Decline transaction successfully.")
                .data(transactionResponse)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping(AppPath.TRANSACTION_PAY)
    public ResponseEntity<?> payTransaction(@PathVariable String transactionId) {
        TransactionResponse transactionResponse = transactionService.payTransaction(transactionId);
        CommonResponse<TransactionResponse> response = CommonResponse.<TransactionResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Pay transaction successfully.")
                .data(transactionResponse)
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
