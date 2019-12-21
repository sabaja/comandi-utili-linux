@Mapper(componentModel = "spring")
public interface MapperAssemblerModelloQuestionario extends BaseMapper<GetModelloQuestionarioOutputBin, QuestionarioResource>{
    @Mapping(target = "modelloQuestionarioObblighi.idProcesso", source = "idProcesso")
    @Mapping(target = "modelloQuestionarioObblighi.idQuestionario", source = "idQuestionario")
    @Mapping(target = "modelloQuestionarioObblighi.codQuestionario", source = "codQuestionario")
    @Mapping(target = "modelloQuestionarioObblighi.listSezioni", source = "listSezioni")
    QuestionarioResource dtoToResource(GetModelloQuestionarioOutputBin model);
}
