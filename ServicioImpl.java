package lsi.ubu.servicios;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lsi.ubu.util.PoolDeConexiones;

public class ServicioImpl implements Servicio {
	private static final Logger LOGGER = LoggerFactory.getLogger(ServicioImpl.class);

	@Override
	public void anularBillete(Time hora, java.util.Date fecha, String origen, String destino, int nroPlazas, int ticket)
			throws SQLException {
		PoolDeConexiones pool = PoolDeConexiones.getInstance();

		/* Conversiones de fechas y horas */
		java.sql.Date fechaSqlDate = new java.sql.Date(fecha.getTime());
		java.sql.Timestamp horaTimestamp = new java.sql.Timestamp(hora.getTime());
		/*int idx=1;
		Connection con = pool.getConnection();
		PreparedStatement st = con.prepareStatement("select idViaje from tickets where idTicket = ?");
		st.setInt(1, ticket);
		ResultSet rs = st.executeQuery();
		System.out.println(rs);*/
		
		

		// A completar por el alumno
	}

	@Override
	public void comprarBillete(Time hora, Date fecha, String origen, String destino, int nroPlazas)
			throws SQLException {
		PoolDeConexiones pool = PoolDeConexiones.getInstance();

		/* Conversiones de fechas y horas */
		java.sql.Date fechaSqlDate = new java.sql.Date(fecha.getTime());
		java.sql.Timestamp horaTimestamp = new java.sql.Timestamp(hora.getTime());

		int idx=1;
		Connection con = pool.getConnection();
		PreparedStatement st1=con.prepareStatement("select idRecorrido from recorridos where estacionOrigen=? and estacionDestino=? and horaSalida=?");
		st1.setString(idx++, origen);
		st1.setString(idx++, destino);
		st1.setTimestamp(idx++, horaTimestamp);
		int sol = st1.executeUpdate();
		System.out.println(sol);
		/*idx=1;
		st1=con.prepareStatement("select idViaje from viajes where idRecorrido=? and fecha=?");
		st1.setInt(idx++,rs);
		PreparedStatement st = con.prepareStatement("INSERT INTO tickets VALUES (seq_tickets.nextval,?,?,?,?)");
		st.setDate(idx++, fechaSqlDate);
		st.setString(idx++, origen);
		st.setString(idx++, destino);
		ResultSet rs = st.executeQuery();*/

		// A completar por el alumno
	}

}
