package com.pucpr.br.bsi2015.tcc.selfiecode.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import com.pucpr.br.bsi2015.tcc.selfiecode.connection.ConnectionFactory;
import com.pucpr.br.bsi2015.tcc.selfiecode.model.CodigoFonte;
import com.pucpr.br.bsi2015.tcc.selfiecode.model.Intervalo;
import com.pucpr.br.bsi2015.tcc.selfiecode.model.Metrica;

public class MetricaDAO {

	public void preencherIntervalos(Metrica m) {
		Connection cf = ConnectionFactory.getConnection();
		Intervalo i;
		List<Intervalo> intervalos = new ArrayList<Intervalo>();
		if (cf == null)
			JOptionPane.showConfirmDialog(null, "ERRRROUUU");
		else {
			String selectSQL = "SELECT * FROM INTERVALO I, METRICA M where I.FK_ID_METRICA = M.ID_METRICA AND M.SIGLA = ? LIMIT 0, 1000;";
			PreparedStatement preparedStatement;
			try {
				
				preparedStatement = cf.prepareStatement(selectSQL);
				preparedStatement.setString(1, m.getSigla());
				
				
				
				ResultSet rs = preparedStatement.executeQuery();
				while(rs.next())
				{
					i = new Intervalo();
					m.setNomeMetrica(rs.getString("NOME_METRICA"));
					i.setValorMinimo(rs.getFloat("VALOR_MINIMO"));
					i.setValorMax(rs.getFloat("VALOR_MAXIMO"));
					i.setDataIntervalo(rs.getDate("DATA_INTERVALO"));
					intervalos.add(i);
				}
				m.setIntervalos(intervalos);
				
				cf.close();
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public List<Metrica> selectMetricas(CodigoFonte cfo) {
		Connection cf = ConnectionFactory.getConnection();

		List<Metrica> metricas = new ArrayList<Metrica>();
		Metrica metrica = null;
		if (cf == null)
			JOptionPane.showConfirmDialog(null, "ERRRROUUU");
		else {
			String selectSQL = "SELECT ID_METRICA, NOME_METRICA, SIGLA, VALOR_METRICA FROM METRICA M, CODIGO_METRICA CM where CM.FK_ID_METRICA = M.ID_METRICA "
					+ "AND CM.FK_ID_CODIGO = ? ";

			PreparedStatement preparedStatement;
			try {
				
				preparedStatement = cf.prepareStatement(selectSQL);
				preparedStatement.setInt(1, cfo.getId());
				
				
				
				ResultSet rs = preparedStatement.executeQuery();
				while(rs.next())
				{
					metrica = new Metrica();
					metrica.setNomeMetrica(rs.getString("NOME_METRICA"));
					metrica.setValorMetrica(rs.getFloat("VALOR_METRICA"));
					metrica.setSigla(rs.getString("SIGLA"));
					metrica.setId(rs.getInt("ID_METRICA"));
					
				
					metricas.add(metrica);
				}
								
				cf.close();
				return metricas;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
}
