package br.com.dendesofthouse.dendeeventos.mappers;

import br.com.dendesofthouse.dendeeventos.dtos.organizador.*;
import br.com.dendesofthouse.dendeeventos.models.Empresa;
import br.com.dendesofthouse.dendeeventos.models.Organizador;
import org.mapstruct.*;

import java.time.LocalDate;
import java.time.Period;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface OrganizadorMapper {

    @Mapping(target = "id", ignore = true) //ID gerado pelo banco
    @Mapping(target = "empresa", source = "empresa", qualifiedByName = "toEmpresaEntity") //Usa um método específico para lidar com o campo empresa.
    Organizador toModel(CadastrarOrganizadorDto dto); //Cria um objeto com dados do DTO


    @Mapping(target = "empresa", source = "empresa", qualifiedByName = "toEmpresaEntity") //Usa um método específico para lidar com o campo empresa.
    void updateModel(@MappingTarget Organizador organizador, AtualizarOrganizadorDto dto); //Atualiza um objeto com os dados do DTO.


    @Mapping(target = "idade", expression = "java(calcularIdade(organizador.getDataNascimento()))") //Usa um método específico para calcular a idade.
    @Mapping(target = "empresa", source = "empresa", qualifiedByName = "toEmpresaDto") //usa um método específico para converter os dados do objeto em dto de saída.
    VisualizarOrganizadorDto toVisualizarDto(Organizador organizador);

    @Mapping(source = "organizador.id", target = "organizadorId") //mapear o id com o campo do DTO.
    @Mapping(source = "organizador.ativo", target = "ativo") //O mesmo do de cima, porém com o campo ativo, provavelmente não necessario.
    StatusOrganizadorDto toStatusDto(String mensagem, Organizador organizador); //DTO de saída de dados.

    @Named("toEmpresaEntity")
    default Empresa toEmpresaEntity(EmpresaDto empresaDto) {
        if (empresaDto == null) return null;
        Empresa empresa = new Empresa();
        empresa.setCnpj(empresaDto.getCnpj());
        empresa.setRazaoSocial(empresaDto.getRazaoSocial());
        empresa.setNomeFantasia(empresaDto.getNomeFantasia());
        return empresa;
    }//método para converter DTO em objeto, para empresa que é associado diretamente ao organizador no DTO.

    @Named("toEmpresaDto")
    default EmpresaDto toEmpresaDto(Empresa empresa) {
        if (empresa == null) return null;
        return new EmpresaDto(
                empresa.getCnpj(),
                empresa.getRazaoSocial(),
                empresa.getNomeFantasia()
        );
    }//O contrario do método acima, objeto --> DTO.

    private String calcularIdade(LocalDate nascimento) {
        Period p = Period.between(nascimento, LocalDate.now());
        return p.getYears() + " anos, " +
                p.getMonths() + " meses, " +
                p.getDays() + " dias";
    }
}
