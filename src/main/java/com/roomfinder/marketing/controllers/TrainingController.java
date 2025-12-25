package com.roomfinder.marketing.controllers;

import com.roomfinder.marketing.dto.GenericResponseAI;
import com.roomfinder.marketing.model.GenericApiResponse;
import com.roomfinder.marketing.facade.TrainingFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Training Controller", description = "API for training the chatbot with room-related datasets.")
@Slf4j
@RestController
@RequestMapping("/train")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TrainingController {

    TrainingFacade trainingFacade;

    @Operation(
            summary = "Get list of room names",
            description = "Returns all available room types. This dataset is used to train the chatbot to recognize room names."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Success",
            content = @Content(schema = @Schema(implementation = GenericApiResponse.class))
    )
    @GetMapping("/roomInfoName")
    public GenericApiResponse<GenericResponseAI> getRoomIntents() {
        var result = trainingFacade.getRoomIntents();
        return GenericApiResponse.success(result);
    }

    @Operation(
            summary = "Get room addresses",
            description = "Provides addresses corresponding to each room type. Used to train the chatbot to answer address-related queries."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Success",
            content = @Content(schema = @Schema(implementation = GenericApiResponse.class))
    )
    @GetMapping("/address")
    public GenericApiResponse<GenericResponseAI> getRoomIntentsWithAddress() {
        var result = trainingFacade.getRoomIntentsWithAddress();
        return GenericApiResponse.success(result);
    }

    @Operation(
            summary = "Get room status information",
            description = "Returns the status of rooms (e.g., available, rented)."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Success",
            content = @Content(schema = @Schema(implementation = GenericApiResponse.class))
    )
    @GetMapping("/status")
    public GenericApiResponse<GenericResponseAI> getRoomIntentsStatus() {
        var result = trainingFacade.getRoomIntentsStatus();
        return GenericApiResponse.success(result);
    }

    @Operation(
            summary = "Get room size and total area",
            description = "Provides information such as length, width, and total area of rooms. Useful for chatbot training related to room dimensions."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Success",
            content = @Content(schema = @Schema(implementation = GenericApiResponse.class))
    )
    @GetMapping("/area")
    public GenericApiResponse<GenericResponseAI> getRoomIntentsTotalArea() {
        var result = trainingFacade.getRoomIntentsTotalArea();
        return GenericApiResponse.success(result);
    }

    @Operation(
            summary = "Get information about room owners",
            description = "Provides basic information about the room owner (name, phone number). Helps the chatbot answer queries related to ownership."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Success",
            content = @Content(schema = @Schema(implementation = GenericApiResponse.class))
    )
    @GetMapping("/infoOwner")
    public GenericApiResponse<GenericResponseAI> getRoomIntentsInfoUser() {
        var result = trainingFacade.getRoomIntentsInfoUser();
        return GenericApiResponse.success(result);
    }

    @Operation(
            summary = "Get room utilities and amenities",
            description = "Returns a list of amenities included in each room, such as furniture and other conveniences."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Success",
            content = @Content(schema = @Schema(implementation = GenericApiResponse.class))
    )
    @GetMapping("/utility")
    public GenericApiResponse<GenericResponseAI> getRoomIntentsUtility() {
        var result = trainingFacade.getRoomIntentsUtility();
        return GenericApiResponse.success(result);
    }

    @Operation(
            summary = "Get detailed pricing information",
            description = "Provides all pricing details such as base price, electricity fees, water fees, and any additional charges."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Success",
            content = @Content(schema = @Schema(implementation = GenericApiResponse.class))
    )
    @GetMapping("/pricingDetails")
    public GenericApiResponse<GenericResponseAI> getRoomIntentsPricingDetails() {
        var result = trainingFacade.getRoomIntentsPricingDetails();
        return GenericApiResponse.success(result);
    }
}
