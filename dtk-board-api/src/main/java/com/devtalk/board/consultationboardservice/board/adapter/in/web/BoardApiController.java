package com.devtalk.board.consultationboardservice.board.adapter.in.web;

import com.devtalk.board.consultationboardservice.board.application.port.in.PostUseCase;
import com.devtalk.board.consultationboardservice.global.success.SuccessCode;
import com.devtalk.board.consultationboardservice.global.success.SuccessResponse;
import com.devtalk.board.consultationboardservice.global.success.SuccessResponseWithoutResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.devtalk.board.consultationboardservice.board.adapter.in.web.dto.BoardInput.*;

@Tag(name = "게시판", description = "게시판 API")
@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardApiController {
    private final PostUseCase postUseCase;

    @Operation(summary = "게시판 - 게시글 작성 API", responses = {
            @ApiResponse(description = "Successful Operation", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessResponseWithoutResult.class)))
    })
    @PostMapping
    public ResponseEntity<?> post(@RequestBody PostInput postInput) {
        postUseCase.writePost(postInput);
        return SuccessResponseWithoutResult.toResponseEntity(SuccessCode.BOARD_POST_SUCCESS);
    }

    @Operation(summary = "게시판 - 게시글 조회 API", responses = {
            @ApiResponse(description = "Successful Operation", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessResponse.class)))
    })
    @GetMapping("/{postId}")
    public ResponseEntity<SuccessResponse> getPost(@PathVariable Long postId) {
        return SuccessResponse.toResponseEntity(SuccessCode.BOARD_VIEW_SUCCESS, postUseCase.viewPost(postId));
    }

    @Operation(summary = "게시판 - 사용자ID로 게시글 조회 API", responses = {
            @ApiResponse(description = "Successful Operation", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessResponse.class)))
    })
    @GetMapping
    public ResponseEntity<SuccessResponse> getPostByUserId(@RequestParam("userId") Long userId) {
        return SuccessResponse.toResponseEntity(SuccessCode.USER_POST_LIST_SEARCH_SUCCESS, postUseCase.getPostList(userId));
    }

    @Operation(summary = "게시판 - 게시글 수정 API", responses = {
            @ApiResponse(description = "Successful Operation", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = SuccessResponseWithoutResult.class)))
    })
    @PutMapping("/{postId}")
    public ResponseEntity<?> modifyPost(@PathVariable Long postId, @RequestBody PostInput postInput) {
        postUseCase.modifyPost(postId, postInput);
        return SuccessResponseWithoutResult.toResponseEntity(SuccessCode.BOARD_MODIFY_SUCCESS);
    }
}
