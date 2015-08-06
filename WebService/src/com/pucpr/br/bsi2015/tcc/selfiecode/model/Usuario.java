package com.pucpr.br.bsi2015.tcc.selfiecode.model;

import java.util.Date;

public class Usuario {
	private long cpf;
	private String nome;
	private String login;
	private Date dataNascimento;
	private Date dataCadastro;
	private String senha;
	private TipoUsuario tipoUsuario;
	
	public Usuario()
	{
		
	}
	
	public Usuario(long cpf)
	{
		this.cpf = cpf;
	}
	
	public void adicionarUsuario()
	{
		
	}
	public void alterarUsuario()
	{
		
	}
	
	public long getCpf() {
		return cpf;
	}
	public void setCpf(long cpf) {
		this.cpf = cpf;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Date getDataNascimento() {
		return dataNascimento;
	}
	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	public Date getDataCadastro() {
		return dataCadastro;
	}
	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public TipoUsuario getTipoUsuario() {
		return tipoUsuario;
	}
	public void setTipoUsuario(TipoUsuario tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}
	
}
