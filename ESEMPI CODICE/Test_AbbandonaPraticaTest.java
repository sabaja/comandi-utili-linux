    @InjectMocks
    private PraticaAffidamentoManualeController controller;
    @MockBean
    private BeanFactory beanFactory;
    @MockBean
    private PreAvvioPraticaBinFactory preAvvioPraticaBinFactory;
    @MockBean
    private RichiestaChiusuraPraticaBinFactory richiestaChiusuraPraticaBinFactory;
    @MockBean
    private PraticaAffidamentoManualeRichiestaChiusuraPraticaCommand praticaAffidamentoManualeRichiestaChiusuraPraticaCommand;



    @Test
    @Ignore
    public void abbandonaPraticaTest() throws Exception {
        PostRichiestaChiusuraPraticaInputBin modelIn = createMockRichiestaPraticaInputBin();
        String ABBANDONA_PRATICA_URL = "/praticaAffidamentoManuale/" + 1234L + POST_ID_PRATICA.toString() + "/richiestaChiusuraPratica";
        given(richiestaChiusuraPraticaBinFactory.createPostChiusuraPratica(any(BigInteger.class), any(RichiestaChiusuraPraticaDto.class)))
                .willReturn(modelIn);
        given(beanFactory.getBean(any(Class.class), any(PostRichiestaChiusuraPraticaInputBin.class)))
                .willReturn(praticaAffidamentoManualeRichiestaChiusuraPraticaCommand);
        given(praticaAffidamentoManualeRichiestaChiusuraPraticaCommand.execute()).willReturn(getRichiestaChiusuraPraticaMock());
        MvcResult mvcResult = mockMvc.perform(post(ABBANDONA_PRATICA_URL)
                .content(OBJECT_MAPPER.writeValueAsString(modelIn))
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.handler().handlerType(controller.getClass()))
                .andExpect(MockMvcResultMatchers.handler().methodName("richiestaChiusuraPratica"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.idPratica").value(POST_ID_PRATICA.toString()))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        Assert.notNull(mvcResult);
    }

    private PostRichiestaChiusuraPraticaOutputBin getRichiestaChiusuraPraticaMock() {
        PostRichiestaChiusuraPraticaOutputBin postRichiestaChiusuraPraticaOutputBin = new PostRichiestaChiusuraPraticaOutputBin();
        postRichiestaChiusuraPraticaOutputBin.setSuccess(Boolean.TRUE);
        postRichiestaChiusuraPraticaOutputBin.setIdPratica(POST_ID_PRATICA);
        postRichiestaChiusuraPraticaOutputBin.setCodPraticaPef("codPraticaPef");
        postRichiestaChiusuraPraticaOutputBin.setCodAbi("codAbi");
        return postRichiestaChiusuraPraticaOutputBin;
    }
