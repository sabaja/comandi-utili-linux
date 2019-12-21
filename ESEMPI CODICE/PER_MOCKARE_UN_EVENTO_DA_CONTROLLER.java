

//Per testare l'evento che salva con un oggetto mockato in input
   @PostMapping(value = {"/mockEsitoMcdRwa"}, consumes = {APPLICATION_JSON_VALUE})
    @ApiResponses(value = {
            @ApiResponse(code = HTTP_OK, message = OK),
            @ApiResponse(code = HTTP_INTERNAL_ERROR, message = "Server error"),
            @ApiResponse(code = HTTP_UNAUTHORIZED, message = "Unauthorized"),
            @ApiResponse(code = HTTP_FORBIDDEN, message = "Forbidden"),
            @ApiResponse(code = HTTP_NOT_FOUND, message = "Not Found")
    })
    public ResponseEntity<Void> postRecuperaFidiInEssere(@RequestBody @NotNull EsitoMcdRwa esitoMcdRwa) throws Exception {
        recuperoRisultatoRwaListener.onReceived(creaPayload(esitoMcdRwa), null);
        return ResponseEntity.ok(null);
    }

   


//Get se non funziona la 
    @GetMapping( path = "/mockEsitoMcdRwa")
    @ApiResponses(value = {
            @ApiResponse(code = HTTP_OK, message = OK),
            @ApiResponse(code = HTTP_INTERNAL_ERROR, message = "Server error"),
            @ApiResponse(code = HTTP_UNAUTHORIZED, message = "Unauthorized"),
            @ApiResponse(code = HTTP_FORBIDDEN, message = "Forbidden"),
            @ApiResponse(code = HTTP_NOT_FOUND, message = "Not Found")
    })
    public ResponseEntity<List<Void>> getRecuperaFidiInEssere()
            throws Exception {
        recuperoRisultatoRwaListener.onReceived(creaPayload(mockSituazioneCreditiziaModel()), null);
        return ok(null);
    }
    private EsitoMcdRwa mockSituazioneCreditiziaModel() throws IOException {
        EsitoMcdRwa situazioneCreditiziaModel = loadMockEventFidiGf("mocks/rest/json/EsitoMcdRwa.json");
        return situazioneCreditiziaModel;
    }

    private EsitoMcdRwa loadMockEventFidiGf(String path)
            throws java.io.IOException {
        return objectMapperForMocks(objectMapper)
                .readValue(MotoreRWAController.class.getClassLoader().getResourceAsStream(path),
                        EsitoMcdRwa.class);
    }

    private byte[] creaPayload(EsitoMcdRwa esitoMcdRwa) {
        byte[] payload = null;
        try {
//            log.debug("Create playload for RecuperoFormeTecnicheFidoEventListener");
        payload = objectMapper.writeValueAsBytes(esitoMcdRwa);
        } catch (JsonProcessingException e) {
//            log.warn("Errore nella conversione del payload in bytes for RecuperoFormeTecnicheFidoEventListener", e);
        }
        return payload;
    }
}	