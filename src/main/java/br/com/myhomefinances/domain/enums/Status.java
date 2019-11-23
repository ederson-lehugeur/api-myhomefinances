package br.com.myhomefinances.domain.enums;

public enum Status {

	PAGO(1, "Pago"),
	PENDENTE(2, "Pendente"),
	SAQUE(3, "Saque"),
	GANHO(4, "Ganho");

	private Integer codigo;
	private String descricao;

	private Status(Integer codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public static Status toEnum(Integer codigo) {
		if (codigo == null) {
			return null;
		}

		for (Status status : Status.values()) {
			if (codigo.equals(status.getCodigo())) {
				return status;
			}
		}

		throw new IllegalArgumentException("Id inválido: " + codigo);
	}
}
