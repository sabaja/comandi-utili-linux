@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class GetModelloQuestionarioCommand extends BaseCommand<GetModelloQuestionarioOutputBin> implements PraticaDetailHolder, PraticaDetailNeeded, PraticaVersionNeeded {
    private final GetModelloQuestionarioBinIn bin;
    private final PraticaDetail praticaDetail;
   
   @Autowired
    private ModelloQuestionarioService modelloService;
    public GetModelloQuestionarioCommand(GetModelloQuestionarioBinIn bin) {
        this.praticaDetail = new PraticaDetail(bin.getIdPratica());
        this.bin = bin;
    }
    @Override
    public GetModelloQuestionarioOutputBin doExecute() {
        return modelloService.retrieveQuestionario(bin);
    }
    @Override
    public PraticaDetail getPraticaDetail() {
        return praticaDetail;
    }
}
/*
da usare cosi:
private void checkQuestionarioLista(List<QuestionarioModelEntity> questionarioLista) {
    if (CollectionUtils.isEmpty(questionarioLista)) {
        throw new QuestionarioControllerException("Nessun modello trovato", "QUEST001", HttpStatus.NOT_FOUND);
    }
}
e iinserire sotto resource 
error-mapping.properties

# ERRORI CONCESSIONE ORCHESTRAZIONE
error-mapping.errors[RCP001] = La matricola utente attuale non corrisponde con la matricola del proprietario della pratica
*/