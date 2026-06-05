package br.com.dendesofthouse.dendeeventos.mappers;

import br.com.dendesofthouse.dendeeventos.dtos.organizador.*;
import br.com.dendesofthouse.dendeeventos.models.Empresa;
import br.com.dendesofthouse.dendeeventos.models.Organizador;
import org.mapstruct.*;

import java.time.LocalDate;
import java.time.Period;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface OrganizadorMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "ativo", constant = "true")
    @Mapping(target = "empresa", source = "empresa", qualifiedByName = "toEmpresaEntity")
    @Mapping(target = "eventos", ignore = true)
    Organizador toModel(CadastrarOrganizadorDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "ativo", ignore = true)
    @Mapping(target = "empresa", source = "empresa", qualifiedByName = "toEmpresaEntity")
    @Mapping(target = "eventos", ignore = true)
    void updateModel(@MappingTarget Organizador organizador, AtualizarOrganizadorDto dto);

    @Mapping(target = "idade", expression = "java(calcularIdade(organizador.getDataNascimento()))")
    @Mapping(target = "empresa", source = "empresa", qualifiedByName = "toEmpresaDto")
    VisualizarOrganizadorDto toVisualizarDto(Organizador organizador);

    @Mapping(source = "organizador.id", target = "organizadorId")
    @Mapping(source = "organizador.ativo", target = "ativo")
    StatusOrganizadorDto toStatusDto(String mensagem, Organizador organizador);

    @Named("toEmpresaEntity")
    default Empresa toEmpresaEntity(EmpresaDto dto) {
        if (dto == null) return null;
        Empresa empresa = new Empresa();
        empresa.setCnpj(dto.cnpj());
        empresa.setRazaoSocial(dto.razaoSocial());
        empresa.setNomeFantasia(dto.nomeFantasia());
        return empresa;
    }

    @Named("toEmpresaDto")
    default EmpresaDto toEmpresaDto(Empresa empresa) {
        if (empresa == null) return null;
        return new EmpresaDto(
                empresa.getCnpj(),
                empresa.getRazaoSocial(),
                empresa.getNomeFantasia()
        );
    }

    default String calcularIdade(LocalDate nascimento) {
        if (nascimento == null) return null;
        Period p = Period.between(nascimento, LocalDate.now());
        return p.getYears() + " anos, " + p.getMonths() + " meses, " + p.getDays() + " dias";
    }
}