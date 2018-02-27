package ni.gob.minsa.sivin.domain;

import java.util.Date;

/**
 * Encuesta es la clase que representa la encuesta.
 * 
 * Encuesta se relaciona con:
 * 
 * <ul>
 * <li>Segmento
 * <li>Encuestador
 * <li>Supervisor
 * </ul>
 * 
 *  
 * @author      William Avilés
 * @version     1.0
 * @since       1.0
 */

public class Encuesta extends BaseMetaData {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ident;
	private String codigo;
	private Segmento segmento;
	private String latitud;
	private String longitud;
	private Integer numEncuesta;
	private Date fechaEntrevista;
	private String jefeFamilia;
	private String sexJefeFamilia;
	private Integer numPersonas;
	private Encuestador encuestador;
	private Supervisor supervisor;
	//Seccion A
	private String agua;
	private String oagua;
	private Integer cuartos;
	private String lugNecesidades;
	private String olugNecesidades;
	private String usaCocinar;
	private String ousaCocinar;
	private String articulos;
	private String oarticulos;
	//SECCION B
	private String conoceFNac;
	private Date fnacEnt;
	private String edadEnt;
	private String leerEnt;
	private String escribirEnt;
	private String leeescEnt;
	private String nivelEnt;
	private String gradoEnt;
	private String entRealizada;
	private String entEmb;
	private String entEmbUnico;
	private String entDioluz;
	private String entHieacfol;
	private String entMeseshierro;
	private String entRecHierro;
	private String entORecHierro;
	private String tiemHierroUnd;
	private String tiemHierro;
	private String dondeHierro;
	private String dondeHierroBesp;
	private String dondeHierroFesp;
	private String tomadoHierro;
	private String vita;
	private String tiempVita;
	private String evitaEmb;
	private String dondeAntic;
	private String nuevoEmb;
	private String hierro;
	private String dHierro;
	//SECCION C
	private String numNinos;
	private String n1;
	private String n2;
	private String n3;
	private String n4;
	private String n5;
	private String n6;
	private String e1;
	private String e2;
	private String e3;
	private String e4;
	private String e5;
	private String e6;
	private String nselec;
	private String nomselec;
	private Date fnacselec;
	private String eselect;
	private String sexselec;
	private String calim;
	private String vcome;
	private String consalim;
	private String calimenf;
	private String clecheenf;
	//SECCION D
	private String hierron;
	private String thierround;
	private String thierrocant;
	private String fhierro;
	private String ghierro;
	private String donhierro;
	private String donhierrobesp;
	private String donhierrofesp;
	private String fuhierro;
	private Date fuhierror;
	private Date fauhierror;
	private String nvita;
	private String ncvita;
	private String tvitaund;
	private String tvitacant;
	private String ndvita;
	private String ndvitao;
	private String tdvita;
	private Date fuvita;
	private Date fauvita;
	private String ncnut;
	private String ncnutund;
	private String ncnutcant;
	private String doncnut;
	private String doncnutfotro;
	private String parasit;
	private String cvparas;
	private String mParasitario;
	private String otroMpEsp;
	private String donMp;
	private String otroDonMp;
	private String evitarParasito;
	private String oEvitarParasito;
	
	//SECCION E
	private String nlactmat;
	private String sexlmat;
	private String emeseslmat;
	private Date fnaclmat;
	private String pecho;
	private String motNopecho;
	private String motNopechoOtro;
	private String dapecho;
	private String tiempecho;
	private String tiemmama;
	private String tiemmamaMins;
	private String dospechos;
	private String vecespechodia;
	private String vecespechonoche;
	private String elmatexcund;
	private String elmatexccant;
	private String ediopechound;
	private String ediopechocant;
	private String combeb;
	private String ealimund;
	private String ealimcant;
	private String bebeLiq;
	private String reunionPeso;
	private String quienReunionPeso;
	private String quienReunionPesoOtro;
	private String assitioReunionPeso;
	
	//SECCION F
	private String pesoTallaEnt;
	private Float pesoEnt1;
	private Float pesoEnt2;
	private Float tallaEnt1;
	private Float tallaEnt2;
	private String pesoTallaNin;
	private Float pesoNin1;
	private Float pesoNin2;
	private Float longNin1;
	private Float longNin2;
	private Float tallaNin1;
	private Float tallaNin2;
	// SECCION G
	private String msEnt;
	private String codMsEnt;
	private Float hbEnt;
	private String msNin;
	private String codMsNin;
	private Float hbNin;
	private String moEnt;
	private String codMoEnt;
	private String pan;
	private String sal;
	private String marcaSal;
	private String azucar;
	private String marcaAzucar;
	// SECCION H
	private String patConsAzuc;
	private String patConsAzucFrec;
	private String patConsSal;
	private String patConsSalFrec;
	private String patConsArroz;
	private String patConsArrozFrec;
	private String patConsAcei;
	private String patConsAceiFrec;
	private String patConsFri;
	private String patConsFriFrec;
	private String patConsCebo;
	private String patConsCeboFrec;
	private String patConsChi;
	private String patConsChiFrec;
	private String patConsQue;
	private String patConsQueFrec;
	private String patConsCafe;
	private String patConsCafeFrec;
	private String patConsTor;
	private String patConsTorFrec;
	private String patConsCar;
	private String patConsCarFrec;
	private String patConsHue;
	private String patConsHueFrec;
	private String patConsPan;
	private String patConsPanFrec;
	private String patConsBan;
	private String patConsBanFrec;
	private String patConsPanDul;
	private String patConsPanDulFrec;
	private String patConsPla;
	private String patConsPlaFrec;
	private String patConsPapa;
	private String patConsPapaFrec;
	private String patConsLec;
	private String patConsLecFrec;
	private String patConsSalTom;
	private String patConsSalTomFrec;
	private String patConsGas;
	private String patConsGasFrec;
	private String patConsCarRes;
	private String patConsCarResFrec;
	private String patConsAvena;
	private String patConsAvenaFrec;
	private String patConsNaran;
	private String patConsNaranFrec;
	private String patConsPasta;
	private String patConsPastaFrec;
	private String patConsAyote;
	private String patConsAyoteFrec;
	private String patConsMagg;
	private String patConsMaggFrec;
	private String patConsFrut;
	private String patConsFrutFrec;
	private String patConsRaic;
	private String patConsRaicFrec;
	private String patConsMenei;
	private String patConsMeneiFrec;
	private String patConsRepollo;
	private String patConsRepolloFrec;
	private String patConsZana;
	private String patConsZanaFrec;
	private String patConsPinolillo;
	private String patConsPinolilloFrec;
	private String patConsOVerd;
	private String patConsOVerdFrec;
	private String patConsPesc;
	private String patConsPescFrec;
	private String patConsAlimComp;
	private String patConsAlimCompFrec;
	private String patConsLecPol;
	private String patConsLecPolFrec;
	private String patConsCarCer;
	private String patConsCarCerFrec;
	private String patConsEmb;
	private String patConsEmbFrec;
	private String patConsMar;
	private String patConsMarFrec;
	private String patConsCarCaza;
	private String patConsCarCazaFrec;
	
	public Encuesta() {
		super();
	}

	public String getIdent() {
		return ident;
	}

	public void setIdent(String ident) {
		this.ident = ident;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
	public Segmento getSegmento() {
		return segmento;
	}

	public void setSegmento(Segmento segmento) {
		this.segmento = segmento;
	}

	public String getLatitud() {
		return latitud;
	}

	public void setLatitud(String latitud) {
		this.latitud = latitud;
	}

	public String getLongitud() {
		return longitud;
	}

	public void setLongitud(String longitud) {
		this.longitud = longitud;
	}

	public Integer getNumEncuesta() {
		return numEncuesta;
	}

	public void setNumEncuesta(Integer numEncuesta) {
		this.numEncuesta = numEncuesta;
	}

	public Date getFechaEntrevista() {
		return fechaEntrevista;
	}

	public void setFechaEntrevista(Date fechaEntrevista) {
		this.fechaEntrevista = fechaEntrevista;
	}

	public String getJefeFamilia() {
		return jefeFamilia;
	}

	public void setJefeFamilia(String jefeFamilia) {
		this.jefeFamilia = jefeFamilia;
	}

	public String getSexJefeFamilia() {
		return sexJefeFamilia;
	}

	public void setSexJefeFamilia(String sexJefeFamilia) {
		this.sexJefeFamilia = sexJefeFamilia;
	}

	public Integer getNumPersonas() {
		return numPersonas;
	}

	public void setNumPersonas(Integer numPersonas) {
		this.numPersonas = numPersonas;
	}

	public Encuestador getEncuestador() {
		return encuestador;
	}

	public void setEncuestador(Encuestador encuestador) {
		this.encuestador = encuestador;
	}

	public Supervisor getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(Supervisor supervisor) {
		this.supervisor = supervisor;
	}

	public String getAgua() {
		return agua;
	}

	public void setAgua(String agua) {
		this.agua = agua;
	}

	public String getOagua() {
		return oagua;
	}

	public void setOagua(String oagua) {
		this.oagua = oagua;
	}

	public Integer getCuartos() {
		return cuartos;
	}

	public void setCuartos(Integer cuartos) {
		this.cuartos = cuartos;
	}

	public String getLugNecesidades() {
		return lugNecesidades;
	}

	public void setLugNecesidades(String lugNecesidades) {
		this.lugNecesidades = lugNecesidades;
	}

	public String getOlugNecesidades() {
		return olugNecesidades;
	}

	public void setOlugNecesidades(String olugNecesidades) {
		this.olugNecesidades = olugNecesidades;
	}

	public String getUsaCocinar() {
		return usaCocinar;
	}

	public void setUsaCocinar(String usaCocinar) {
		this.usaCocinar = usaCocinar;
	}

	public String getOusaCocinar() {
		return ousaCocinar;
	}

	public void setOusaCocinar(String ousaCocinar) {
		this.ousaCocinar = ousaCocinar;
	}

	public String getArticulos() {
		return articulos;
	}

	public void setArticulos(String articulos) {
		this.articulos = articulos;
	}

	public String getOarticulos() {
		return oarticulos;
	}

	public void setOarticulos(String oarticulos) {
		this.oarticulos = oarticulos;
	}

	
	public String getConoceFNac() {
		return conoceFNac;
	}

	public void setConoceFNac(String conoceFNac) {
		this.conoceFNac = conoceFNac;
	}

	public Date getFnacEnt() {
		return fnacEnt;
	}

	public void setFnacEnt(Date fnacEnt) {
		this.fnacEnt = fnacEnt;
	}

	public String getEdadEnt() {
		return edadEnt;
	}

	public void setEdadEnt(String edadEnt) {
		this.edadEnt = edadEnt;
	}

	public String getLeerEnt() {
		return leerEnt;
	}

	public void setLeerEnt(String leerEnt) {
		this.leerEnt = leerEnt;
	}

	public String getEscribirEnt() {
		return escribirEnt;
	}

	public void setEscribirEnt(String escribirEnt) {
		this.escribirEnt = escribirEnt;
	}

	public String getLeeescEnt() {
		return leeescEnt;
	}

	public void setLeeescEnt(String leeescEnt) {
		this.leeescEnt = leeescEnt;
	}

	public String getNivelEnt() {
		return nivelEnt;
	}

	public void setNivelEnt(String nivelEnt) {
		this.nivelEnt = nivelEnt;
	}

	public String getGradoEnt() {
		return gradoEnt;
	}

	public void setGradoEnt(String gradoEnt) {
		this.gradoEnt = gradoEnt;
	}

	public String getEntRealizada() {
		return entRealizada;
	}

	public void setEntRealizada(String entRealizada) {
		this.entRealizada = entRealizada;
	}

	public String getEntEmb() {
		return entEmb;
	}

	public void setEntEmb(String entEmb) {
		this.entEmb = entEmb;
	}
	
	public String getEntEmbUnico() {
		return entEmbUnico;
	}

	public void setEntEmbUnico(String entEmbUnico) {
		this.entEmbUnico = entEmbUnico;
	}

	public String getEntDioluz() {
		return entDioluz;
	}

	public void setEntDioluz(String entDioluz) {
		this.entDioluz = entDioluz;
	}

	public String getEntHieacfol() {
		return entHieacfol;
	}

	public void setEntHieacfol(String entHieacfol) {
		this.entHieacfol = entHieacfol;
	}

	public String getEntMeseshierro() {
		return entMeseshierro;
	}

	public void setEntMeseshierro(String entMeseshierro) {
		this.entMeseshierro = entMeseshierro;
	}

	public String getEntRecHierro() {
		return entRecHierro;
	}

	public void setEntRecHierro(String entRecHierro) {
		this.entRecHierro = entRecHierro;
	}

	public String getEntORecHierro() {
		return entORecHierro;
	}

	public void setEntORecHierro(String entORecHierro) {
		this.entORecHierro = entORecHierro;
	}

	public String getTiemHierroUnd() {
		return tiemHierroUnd;
	}

	public void setTiemHierroUnd(String tiemHierroUnd) {
		this.tiemHierroUnd = tiemHierroUnd;
	}

	public String getTiemHierro() {
		return tiemHierro;
	}

	public void setTiemHierro(String tiemHierro) {
		this.tiemHierro = tiemHierro;
	}

	public String getDondeHierro() {
		return dondeHierro;
	}

	public void setDondeHierro(String dondeHierro) {
		this.dondeHierro = dondeHierro;
	}

	public String getDondeHierroBesp() {
		return dondeHierroBesp;
	}

	public void setDondeHierroBesp(String dondeHierroBesp) {
		this.dondeHierroBesp = dondeHierroBesp;
	}

	public String getDondeHierroFesp() {
		return dondeHierroFesp;
	}

	public void setDondeHierroFesp(String dondeHierroFesp) {
		this.dondeHierroFesp = dondeHierroFesp;
	}

	public String getTomadoHierro() {
		return tomadoHierro;
	}

	public void setTomadoHierro(String tomadoHierro) {
		this.tomadoHierro = tomadoHierro;
	}

	public String getVita() {
		return vita;
	}

	public void setVita(String vita) {
		this.vita = vita;
	}

	public String getTiempVita() {
		return tiempVita;
	}

	public void setTiempVita(String tiempVita) {
		this.tiempVita = tiempVita;
	}

	public String getEvitaEmb() {
		return evitaEmb;
	}

	public void setEvitaEmb(String evitaEmb) {
		this.evitaEmb = evitaEmb;
	}

	public String getDondeAntic() {
		return dondeAntic;
	}

	public void setDondeAntic(String dondeAntic) {
		this.dondeAntic = dondeAntic;
	}

	public String getNuevoEmb() {
		return nuevoEmb;
	}

	public void setNuevoEmb(String nuevoEmb) {
		this.nuevoEmb = nuevoEmb;
	}

	public String getHierro() {
		return hierro;
	}

	public void setHierro(String hierro) {
		this.hierro = hierro;
	}

	public String getdHierro() {
		return dHierro;
	}

	public void setdHierro(String dHierro) {
		this.dHierro = dHierro;
	}
	
	public String getNumNinos() {
		return numNinos;
	}

	public void setNumNinos(String numNinos) {
		this.numNinos = numNinos;
	}

	public String getN6() {
		return n6;
	}

	public void setN6(String n6) {
		this.n6 = n6;
	}

	public String getE6() {
		return e6;
	}

	public void setE6(String e6) {
		this.e6 = e6;
	}

	public String getN1() {
		return n1;
	}

	public void setN1(String n1) {
		this.n1 = n1;
	}

	public String getN2() {
		return n2;
	}

	public void setN2(String n2) {
		this.n2 = n2;
	}

	public String getN3() {
		return n3;
	}

	public void setN3(String n3) {
		this.n3 = n3;
	}

	public String getN4() {
		return n4;
	}

	public void setN4(String n4) {
		this.n4 = n4;
	}

	public String getN5() {
		return n5;
	}

	public void setN5(String n5) {
		this.n5 = n5;
	}

	public String getE1() {
		return e1;
	}

	public void setE1(String e1) {
		this.e1 = e1;
	}

	public String getE2() {
		return e2;
	}

	public void setE2(String e2) {
		this.e2 = e2;
	}

	public String getE3() {
		return e3;
	}

	public void setE3(String e3) {
		this.e3 = e3;
	}

	public String getE4() {
		return e4;
	}

	public void setE4(String e4) {
		this.e4 = e4;
	}

	public String getE5() {
		return e5;
	}

	public void setE5(String e5) {
		this.e5 = e5;
	}

	public String getNselec() {
		return nselec;
	}

	public void setNselec(String nselec) {
		this.nselec = nselec;
	}

	public String getNomselec() {
		return nomselec;
	}

	public void setNomselec(String nomselec) {
		this.nomselec = nomselec;
	}

	public Date getFnacselec() {
		return fnacselec;
	}

	public void setFnacselec(Date fnacselec) {
		this.fnacselec = fnacselec;
	}

	public String getEselect() {
		return eselect;
	}

	public void setEselect(String eselect) {
		this.eselect = eselect;
	}

	public String getSexselec() {
		return sexselec;
	}

	public void setSexselec(String sexselec) {
		this.sexselec = sexselec;
	}

	public String getCalim() {
		return calim;
	}

	public void setCalim(String calim) {
		this.calim = calim;
	}

	public String getVcome() {
		return vcome;
	}

	public void setVcome(String vcome) {
		this.vcome = vcome;
	}

	public String getConsalim() {
		return consalim;
	}

	public void setConsalim(String consalim) {
		this.consalim = consalim;
	}

	public String getCalimenf() {
		return calimenf;
	}

	public void setCalimenf(String calimenf) {
		this.calimenf = calimenf;
	}

	public String getClecheenf() {
		return clecheenf;
	}

	public void setClecheenf(String clecheenf) {
		this.clecheenf = clecheenf;
	}
	
	public String getHierron() {
		return hierron;
	}

	public void setHierron(String hierron) {
		this.hierron = hierron;
	}

	public String getThierround() {
		return thierround;
	}

	public void setThierround(String thierround) {
		this.thierround = thierround;
	}

	public String getThierrocant() {
		return thierrocant;
	}

	public void setThierrocant(String thierrocant) {
		this.thierrocant = thierrocant;
	}

	public String getFhierro() {
		return fhierro;
	}

	public void setFhierro(String fhierro) {
		this.fhierro = fhierro;
	}

	public String getGhierro() {
		return ghierro;
	}

	public void setGhierro(String ghierro) {
		this.ghierro = ghierro;
	}

	public String getDonhierro() {
		return donhierro;
	}

	public void setDonhierro(String donhierro) {
		this.donhierro = donhierro;
	}

	public String getDonhierrobesp() {
		return donhierrobesp;
	}

	public void setDonhierrobesp(String donhierrobesp) {
		this.donhierrobesp = donhierrobesp;
	}

	public String getDonhierrofesp() {
		return donhierrofesp;
	}

	public void setDonhierrofesp(String donhierrofesp) {
		this.donhierrofesp = donhierrofesp;
	}

	public String getFuhierro() {
		return fuhierro;
	}

	public void setFuhierro(String fuhierro) {
		this.fuhierro = fuhierro;
	}

	public Date getFuhierror() {
		return fuhierror;
	}

	public void setFuhierror(Date fuhierror) {
		this.fuhierror = fuhierror;
	}

	public Date getFauhierror() {
		return fauhierror;
	}

	public void setFauhierror(Date fauhierror) {
		this.fauhierror = fauhierror;
	}

	public String getNvita() {
		return nvita;
	}

	public void setNvita(String nvita) {
		this.nvita = nvita;
	}

	public String getNcvita() {
		return ncvita;
	}

	public void setNcvita(String ncvita) {
		this.ncvita = ncvita;
	}

	public String getTvitaund() {
		return tvitaund;
	}

	public void setTvitaund(String tvitaund) {
		this.tvitaund = tvitaund;
	}

	public String getTvitacant() {
		return tvitacant;
	}

	public void setTvitacant(String tvitacant) {
		this.tvitacant = tvitacant;
	}

	public String getNdvita() {
		return ndvita;
	}

	public void setNdvita(String ndvita) {
		this.ndvita = ndvita;
	}

	public String getNdvitao() {
		return ndvitao;
	}

	public void setNdvitao(String ndvitao) {
		this.ndvitao = ndvitao;
	}

	public String getTdvita() {
		return tdvita;
	}

	public void setTdvita(String tdvita) {
		this.tdvita = tdvita;
	}

	public Date getFuvita() {
		return fuvita;
	}

	public void setFuvita(Date fuvita) {
		this.fuvita = fuvita;
	}

	public Date getFauvita() {
		return fauvita;
	}

	public void setFauvita(Date fauvita) {
		this.fauvita = fauvita;
	}

	public String getNcnut() {
		return ncnut;
	}

	public void setNcnut(String ncnut) {
		this.ncnut = ncnut;
	}

	public String getNcnutund() {
		return ncnutund;
	}

	public void setNcnutund(String ncnutund) {
		this.ncnutund = ncnutund;
	}

	public String getNcnutcant() {
		return ncnutcant;
	}

	public void setNcnutcant(String ncnutcant) {
		this.ncnutcant = ncnutcant;
	}

	public String getDoncnut() {
		return doncnut;
	}

	public void setDoncnut(String doncnut) {
		this.doncnut = doncnut;
	}

	public String getDoncnutfotro() {
		return doncnutfotro;
	}

	public void setDoncnutfotro(String doncnutfotro) {
		this.doncnutfotro = doncnutfotro;
	}

	public String getParasit() {
		return parasit;
	}

	public void setParasit(String parasit) {
		this.parasit = parasit;
	}

	public String getCvparas() {
		return cvparas;
	}

	public void setCvparas(String cvparas) {
		this.cvparas = cvparas;
	}

	public String getmParasitario() {
		return mParasitario;
	}

	public void setmParasitario(String mParasitario) {
		this.mParasitario = mParasitario;
	}

	public String getOtroMpEsp() {
		return otroMpEsp;
	}

	public void setOtroMpEsp(String otroMpEsp) {
		this.otroMpEsp = otroMpEsp;
	}

	public String getDonMp() {
		return donMp;
	}

	public void setDonMp(String donMp) {
		this.donMp = donMp;
	}
	

	public String getOtroDonMp() {
		return otroDonMp;
	}

	public void setOtroDonMp(String otroDonMp) {
		this.otroDonMp = otroDonMp;
	}

	public String getEvitarParasito() {
		return evitarParasito;
	}

	public void setEvitarParasito(String evitarParasito) {
		this.evitarParasito = evitarParasito;
	}

	public String getoEvitarParasito() {
		return oEvitarParasito;
	}

	public void setoEvitarParasito(String oEvitarParasito) {
		this.oEvitarParasito = oEvitarParasito;
	}

	public String getNlactmat() {
		return nlactmat;
	}

	public void setNlactmat(String nlactmat) {
		this.nlactmat = nlactmat;
	}

	public String getSexlmat() {
		return sexlmat;
	}

	public void setSexlmat(String sexlmat) {
		this.sexlmat = sexlmat;
	}

	public String getEmeseslmat() {
		return emeseslmat;
	}

	public void setEmeseslmat(String emeseslmat) {
		this.emeseslmat = emeseslmat;
	}

	public Date getFnaclmat() {
		return fnaclmat;
	}

	public void setFnaclmat(Date fnaclmat) {
		this.fnaclmat = fnaclmat;
	}

	public String getPecho() {
		return pecho;
	}

	public void setPecho(String pecho) {
		this.pecho = pecho;
	}

	public String getMotNopecho() {
		return motNopecho;
	}

	public void setMotNopecho(String motNopecho) {
		this.motNopecho = motNopecho;
	}

	public String getMotNopechoOtro() {
		return motNopechoOtro;
	}

	public void setMotNopechoOtro(String motNopechoOtro) {
		this.motNopechoOtro = motNopechoOtro;
	}

	public String getDapecho() {
		return dapecho;
	}

	public void setDapecho(String dapecho) {
		this.dapecho = dapecho;
	}

	public String getTiempecho() {
		return tiempecho;
	}

	public void setTiempecho(String tiempecho) {
		this.tiempecho = tiempecho;
	}

	public String getTiemmama() {
		return tiemmama;
	}

	public void setTiemmama(String tiemmama) {
		this.tiemmama = tiemmama;
	}

	public String getTiemmamaMins() {
		return tiemmamaMins;
	}

	public void setTiemmamaMins(String tiemmamaMins) {
		this.tiemmamaMins = tiemmamaMins;
	}

	public String getDospechos() {
		return dospechos;
	}

	public void setDospechos(String dospechos) {
		this.dospechos = dospechos;
	}

	public String getVecespechodia() {
		return vecespechodia;
	}

	public void setVecespechodia(String vecespechodia) {
		this.vecespechodia = vecespechodia;
	}

	public String getVecespechonoche() {
		return vecespechonoche;
	}

	public void setVecespechonoche(String vecespechonoche) {
		this.vecespechonoche = vecespechonoche;
	}

	public String getElmatexcund() {
		return elmatexcund;
	}

	public void setElmatexcund(String elmatexcund) {
		this.elmatexcund = elmatexcund;
	}

	public String getElmatexccant() {
		return elmatexccant;
	}

	public void setElmatexccant(String elmatexccant) {
		this.elmatexccant = elmatexccant;
	}

	public String getEdiopechound() {
		return ediopechound;
	}

	public void setEdiopechound(String ediopechound) {
		this.ediopechound = ediopechound;
	}

	public String getEdiopechocant() {
		return ediopechocant;
	}

	public void setEdiopechocant(String ediopechocant) {
		this.ediopechocant = ediopechocant;
	}

	public String getCombeb() {
		return combeb;
	}

	public void setCombeb(String combeb) {
		this.combeb = combeb;
	}

	public String getEalimund() {
		return ealimund;
	}

	public void setEalimund(String ealimund) {
		this.ealimund = ealimund;
	}

	public String getEalimcant() {
		return ealimcant;
	}

	public void setEalimcant(String ealimcant) {
		this.ealimcant = ealimcant;
	}

	public String getBebeLiq() {
		return bebeLiq;
	}

	public void setBebeLiq(String bebeLiq) {
		this.bebeLiq = bebeLiq;
	}

	public String getReunionPeso() {
		return reunionPeso;
	}

	public void setReunionPeso(String reunionPeso) {
		this.reunionPeso = reunionPeso;
	}

	public String getQuienReunionPeso() {
		return quienReunionPeso;
	}

	public void setQuienReunionPeso(String quienReunionPeso) {
		this.quienReunionPeso = quienReunionPeso;
	}

	public String getQuienReunionPesoOtro() {
		return quienReunionPesoOtro;
	}

	public void setQuienReunionPesoOtro(String quienReunionPesoOtro) {
		this.quienReunionPesoOtro = quienReunionPesoOtro;
	}

	public String getAssitioReunionPeso() {
		return assitioReunionPeso;
	}

	public void setAssitioReunionPeso(String assitioReunionPeso) {
		this.assitioReunionPeso = assitioReunionPeso;
	}

	public Float getPesoEnt1() {
		return pesoEnt1;
	}

	public void setPesoEnt1(Float pesoEnt1) {
		this.pesoEnt1 = pesoEnt1;
	}

	public Float getPesoEnt2() {
		return pesoEnt2;
	}

	public void setPesoEnt2(Float pesoEnt2) {
		this.pesoEnt2 = pesoEnt2;
	}

	public Float getTallaEnt1() {
		return tallaEnt1;
	}

	public void setTallaEnt1(Float tallaEnt1) {
		this.tallaEnt1 = tallaEnt1;
	}

	public Float getTallaEnt2() {
		return tallaEnt2;
	}

	public void setTallaEnt2(Float tallaEnt2) {
		this.tallaEnt2 = tallaEnt2;
	}

	public Float getPesoNin1() {
		return pesoNin1;
	}

	public void setPesoNin1(Float pesoNin1) {
		this.pesoNin1 = pesoNin1;
	}

	public Float getPesoNin2() {
		return pesoNin2;
	}

	public void setPesoNin2(Float pesoNin2) {
		this.pesoNin2 = pesoNin2;
	}

	public Float getLongNin1() {
		return longNin1;
	}

	public void setLongNin1(Float longNin1) {
		this.longNin1 = longNin1;
	}

	public Float getLongNin2() {
		return longNin2;
	}

	public void setLongNin2(Float longNin2) {
		this.longNin2 = longNin2;
	}

	public Float getTallaNin1() {
		return tallaNin1;
	}

	public void setTallaNin1(Float tallaNin1) {
		this.tallaNin1 = tallaNin1;
	}

	public Float getTallaNin2() {
		return tallaNin2;
	}

	public void setTallaNin2(Float tallaNin2) {
		this.tallaNin2 = tallaNin2;
	}

	public String getMsEnt() {
		return msEnt;
	}

	public void setMsEnt(String msEnt) {
		this.msEnt = msEnt;
	}

	public Float getHbEnt() {
		return hbEnt;
	}

	public void setHbEnt(Float hbEnt) {
		this.hbEnt = hbEnt;
	}

	public String getMsNin() {
		return msNin;
	}

	public void setMsNin(String msNin) {
		this.msNin = msNin;
	}

	public Float getHbNin() {
		return hbNin;
	}

	public void setHbNin(Float hbNin) {
		this.hbNin = hbNin;
	}

	public String getMoEnt() {
		return moEnt;
	}

	public void setMoEnt(String moEnt) {
		this.moEnt = moEnt;
	}

	public String getPan() {
		return pan;
	}

	public void setPan(String pan) {
		this.pan = pan;
	}

	public String getSal() {
		return sal;
	}

	public void setSal(String sal) {
		this.sal = sal;
	}

	public String getMarcaSal() {
		return marcaSal;
	}

	public void setMarcaSal(String marcaSal) {
		this.marcaSal = marcaSal;
	}

	public String getAzucar() {
		return azucar;
	}

	public void setAzucar(String azucar) {
		this.azucar = azucar;
	}

	public String getMarcaAzucar() {
		return marcaAzucar;
	}

	public void setMarcaAzucar(String marcaAzucar) {
		this.marcaAzucar = marcaAzucar;
	}
	
	

	public String getCodMsEnt() {
		return codMsEnt;
	}

	public void setCodMsEnt(String codMsEnt) {
		this.codMsEnt = codMsEnt;
	}

	public String getCodMsNin() {
		return codMsNin;
	}

	public void setCodMsNin(String codMsNin) {
		this.codMsNin = codMsNin;
	}

	public String getCodMoEnt() {
		return codMoEnt;
	}

	public void setCodMoEnt(String codMoEnt) {
		this.codMoEnt = codMoEnt;
	}

	public String getPatConsAzuc() {
		return patConsAzuc;
	}

	public void setPatConsAzuc(String patConsAzuc) {
		this.patConsAzuc = patConsAzuc;
	}

	public String getPatConsAzucFrec() {
		return patConsAzucFrec;
	}

	public void setPatConsAzucFrec(String patConsAzucFrec) {
		this.patConsAzucFrec = patConsAzucFrec;
	}

	public String getPatConsSal() {
		return patConsSal;
	}

	public void setPatConsSal(String patConsSal) {
		this.patConsSal = patConsSal;
	}

	public String getPatConsSalFrec() {
		return patConsSalFrec;
	}

	public void setPatConsSalFrec(String patConsSalFrec) {
		this.patConsSalFrec = patConsSalFrec;
	}

	public String getPatConsArroz() {
		return patConsArroz;
	}

	public void setPatConsArroz(String patConsArroz) {
		this.patConsArroz = patConsArroz;
	}

	public String getPatConsArrozFrec() {
		return patConsArrozFrec;
	}

	public void setPatConsArrozFrec(String patConsArrozFrec) {
		this.patConsArrozFrec = patConsArrozFrec;
	}

	public String getPatConsAcei() {
		return patConsAcei;
	}

	public void setPatConsAcei(String patConsAcei) {
		this.patConsAcei = patConsAcei;
	}

	public String getPatConsAceiFrec() {
		return patConsAceiFrec;
	}

	public void setPatConsAceiFrec(String patConsAceiFrec) {
		this.patConsAceiFrec = patConsAceiFrec;
	}

	public String getPatConsFri() {
		return patConsFri;
	}

	public void setPatConsFri(String patConsFri) {
		this.patConsFri = patConsFri;
	}

	public String getPatConsFriFrec() {
		return patConsFriFrec;
	}

	public void setPatConsFriFrec(String patConsFriFrec) {
		this.patConsFriFrec = patConsFriFrec;
	}

	public String getPatConsCebo() {
		return patConsCebo;
	}

	public void setPatConsCebo(String patConsCebo) {
		this.patConsCebo = patConsCebo;
	}

	public String getPatConsCeboFrec() {
		return patConsCeboFrec;
	}

	public void setPatConsCeboFrec(String patConsCeboFrec) {
		this.patConsCeboFrec = patConsCeboFrec;
	}

	public String getPatConsChi() {
		return patConsChi;
	}

	public void setPatConsChi(String patConsChi) {
		this.patConsChi = patConsChi;
	}

	public String getPatConsChiFrec() {
		return patConsChiFrec;
	}

	public void setPatConsChiFrec(String patConsChiFrec) {
		this.patConsChiFrec = patConsChiFrec;
	}

	public String getPatConsQue() {
		return patConsQue;
	}

	public void setPatConsQue(String patConsQue) {
		this.patConsQue = patConsQue;
	}

	public String getPatConsQueFrec() {
		return patConsQueFrec;
	}

	public void setPatConsQueFrec(String patConsQueFrec) {
		this.patConsQueFrec = patConsQueFrec;
	}

	public String getPatConsCafe() {
		return patConsCafe;
	}

	public void setPatConsCafe(String patConsCafe) {
		this.patConsCafe = patConsCafe;
	}

	public String getPatConsCafeFrec() {
		return patConsCafeFrec;
	}

	public void setPatConsCafeFrec(String patConsCafeFrec) {
		this.patConsCafeFrec = patConsCafeFrec;
	}

	public String getPatConsTor() {
		return patConsTor;
	}

	public void setPatConsTor(String patConsTor) {
		this.patConsTor = patConsTor;
	}

	public String getPatConsTorFrec() {
		return patConsTorFrec;
	}

	public void setPatConsTorFrec(String patConsTorFrec) {
		this.patConsTorFrec = patConsTorFrec;
	}

	public String getPatConsCar() {
		return patConsCar;
	}

	public void setPatConsCar(String patConsCar) {
		this.patConsCar = patConsCar;
	}

	public String getPatConsCarFrec() {
		return patConsCarFrec;
	}

	public void setPatConsCarFrec(String patConsCarFrec) {
		this.patConsCarFrec = patConsCarFrec;
	}

	public String getPatConsHue() {
		return patConsHue;
	}

	public void setPatConsHue(String patConsHue) {
		this.patConsHue = patConsHue;
	}

	public String getPatConsHueFrec() {
		return patConsHueFrec;
	}

	public void setPatConsHueFrec(String patConsHueFrec) {
		this.patConsHueFrec = patConsHueFrec;
	}

	public String getPatConsPan() {
		return patConsPan;
	}

	public void setPatConsPan(String patConsPan) {
		this.patConsPan = patConsPan;
	}

	public String getPatConsPanFrec() {
		return patConsPanFrec;
	}

	public void setPatConsPanFrec(String patConsPanFrec) {
		this.patConsPanFrec = patConsPanFrec;
	}

	public String getPatConsBan() {
		return patConsBan;
	}

	public void setPatConsBan(String patConsBan) {
		this.patConsBan = patConsBan;
	}

	public String getPatConsBanFrec() {
		return patConsBanFrec;
	}

	public void setPatConsBanFrec(String patConsBanFrec) {
		this.patConsBanFrec = patConsBanFrec;
	}

	public String getPatConsPanDul() {
		return patConsPanDul;
	}

	public void setPatConsPanDul(String patConsPanDul) {
		this.patConsPanDul = patConsPanDul;
	}

	public String getPatConsPanDulFrec() {
		return patConsPanDulFrec;
	}

	public void setPatConsPanDulFrec(String patConsPanDulFrec) {
		this.patConsPanDulFrec = patConsPanDulFrec;
	}

	public String getPatConsPla() {
		return patConsPla;
	}

	public void setPatConsPla(String patConsPla) {
		this.patConsPla = patConsPla;
	}

	public String getPatConsPlaFrec() {
		return patConsPlaFrec;
	}

	public void setPatConsPlaFrec(String patConsPlaFrec) {
		this.patConsPlaFrec = patConsPlaFrec;
	}

	public String getPatConsPapa() {
		return patConsPapa;
	}

	public void setPatConsPapa(String patConsPapa) {
		this.patConsPapa = patConsPapa;
	}

	public String getPatConsPapaFrec() {
		return patConsPapaFrec;
	}

	public void setPatConsPapaFrec(String patConsPapaFrec) {
		this.patConsPapaFrec = patConsPapaFrec;
	}

	public String getPatConsLec() {
		return patConsLec;
	}

	public void setPatConsLec(String patConsLec) {
		this.patConsLec = patConsLec;
	}

	public String getPatConsLecFrec() {
		return patConsLecFrec;
	}

	public void setPatConsLecFrec(String patConsLecFrec) {
		this.patConsLecFrec = patConsLecFrec;
	}

	public String getPatConsSalTom() {
		return patConsSalTom;
	}

	public void setPatConsSalTom(String patConsSalTom) {
		this.patConsSalTom = patConsSalTom;
	}

	public String getPatConsSalTomFrec() {
		return patConsSalTomFrec;
	}

	public void setPatConsSalTomFrec(String patConsSalTomFrec) {
		this.patConsSalTomFrec = patConsSalTomFrec;
	}

	public String getPatConsGas() {
		return patConsGas;
	}

	public void setPatConsGas(String patConsGas) {
		this.patConsGas = patConsGas;
	}

	public String getPatConsGasFrec() {
		return patConsGasFrec;
	}

	public void setPatConsGasFrec(String patConsGasFrec) {
		this.patConsGasFrec = patConsGasFrec;
	}

	public String getPatConsCarRes() {
		return patConsCarRes;
	}

	public void setPatConsCarRes(String patConsCarRes) {
		this.patConsCarRes = patConsCarRes;
	}

	public String getPatConsCarResFrec() {
		return patConsCarResFrec;
	}

	public void setPatConsCarResFrec(String patConsCarResFrec) {
		this.patConsCarResFrec = patConsCarResFrec;
	}

	public String getPatConsAvena() {
		return patConsAvena;
	}

	public void setPatConsAvena(String patConsAvena) {
		this.patConsAvena = patConsAvena;
	}

	public String getPatConsAvenaFrec() {
		return patConsAvenaFrec;
	}

	public void setPatConsAvenaFrec(String patConsAvenaFrec) {
		this.patConsAvenaFrec = patConsAvenaFrec;
	}

	public String getPatConsNaran() {
		return patConsNaran;
	}

	public void setPatConsNaran(String patConsNaran) {
		this.patConsNaran = patConsNaran;
	}

	public String getPatConsNaranFrec() {
		return patConsNaranFrec;
	}

	public void setPatConsNaranFrec(String patConsNaranFrec) {
		this.patConsNaranFrec = patConsNaranFrec;
	}

	public String getPatConsPasta() {
		return patConsPasta;
	}

	public void setPatConsPasta(String patConsPasta) {
		this.patConsPasta = patConsPasta;
	}

	public String getPatConsPastaFrec() {
		return patConsPastaFrec;
	}

	public void setPatConsPastaFrec(String patConsPastaFrec) {
		this.patConsPastaFrec = patConsPastaFrec;
	}

	public String getPatConsAyote() {
		return patConsAyote;
	}

	public void setPatConsAyote(String patConsAyote) {
		this.patConsAyote = patConsAyote;
	}

	public String getPatConsAyoteFrec() {
		return patConsAyoteFrec;
	}

	public void setPatConsAyoteFrec(String patConsAyoteFrec) {
		this.patConsAyoteFrec = patConsAyoteFrec;
	}

	public String getPatConsMagg() {
		return patConsMagg;
	}

	public void setPatConsMagg(String patConsMagg) {
		this.patConsMagg = patConsMagg;
	}

	public String getPatConsMaggFrec() {
		return patConsMaggFrec;
	}

	public void setPatConsMaggFrec(String patConsMaggFrec) {
		this.patConsMaggFrec = patConsMaggFrec;
	}

	public String getPatConsFrut() {
		return patConsFrut;
	}

	public void setPatConsFrut(String patConsFrut) {
		this.patConsFrut = patConsFrut;
	}

	public String getPatConsFrutFrec() {
		return patConsFrutFrec;
	}

	public void setPatConsFrutFrec(String patConsFrutFrec) {
		this.patConsFrutFrec = patConsFrutFrec;
	}

	public String getPatConsRaic() {
		return patConsRaic;
	}

	public void setPatConsRaic(String patConsRaic) {
		this.patConsRaic = patConsRaic;
	}

	public String getPatConsRaicFrec() {
		return patConsRaicFrec;
	}

	public void setPatConsRaicFrec(String patConsRaicFrec) {
		this.patConsRaicFrec = patConsRaicFrec;
	}

	public String getPatConsMenei() {
		return patConsMenei;
	}

	public void setPatConsMenei(String patConsMenei) {
		this.patConsMenei = patConsMenei;
	}

	public String getPatConsMeneiFrec() {
		return patConsMeneiFrec;
	}

	public void setPatConsMeneiFrec(String patConsMeneiFrec) {
		this.patConsMeneiFrec = patConsMeneiFrec;
	}

	public String getPatConsRepollo() {
		return patConsRepollo;
	}

	public void setPatConsRepollo(String patConsRepollo) {
		this.patConsRepollo = patConsRepollo;
	}

	public String getPatConsRepolloFrec() {
		return patConsRepolloFrec;
	}

	public void setPatConsRepolloFrec(String patConsRepolloFrec) {
		this.patConsRepolloFrec = patConsRepolloFrec;
	}

	public String getPatConsZana() {
		return patConsZana;
	}

	public void setPatConsZana(String patConsZana) {
		this.patConsZana = patConsZana;
	}

	public String getPatConsZanaFrec() {
		return patConsZanaFrec;
	}

	public void setPatConsZanaFrec(String patConsZanaFrec) {
		this.patConsZanaFrec = patConsZanaFrec;
	}

	public String getPatConsPinolillo() {
		return patConsPinolillo;
	}

	public void setPatConsPinolillo(String patConsPinolillo) {
		this.patConsPinolillo = patConsPinolillo;
	}

	public String getPatConsPinolilloFrec() {
		return patConsPinolilloFrec;
	}

	public void setPatConsPinolilloFrec(String patConsPinolilloFrec) {
		this.patConsPinolilloFrec = patConsPinolilloFrec;
	}

	public String getPatConsOVerd() {
		return patConsOVerd;
	}

	public void setPatConsOVerd(String patConsOVerd) {
		this.patConsOVerd = patConsOVerd;
	}

	public String getPatConsOVerdFrec() {
		return patConsOVerdFrec;
	}

	public void setPatConsOVerdFrec(String patConsOVerdFrec) {
		this.patConsOVerdFrec = patConsOVerdFrec;
	}

	public String getPatConsPesc() {
		return patConsPesc;
	}

	public void setPatConsPesc(String patConsPesc) {
		this.patConsPesc = patConsPesc;
	}

	public String getPatConsPescFrec() {
		return patConsPescFrec;
	}

	public void setPatConsPescFrec(String patConsPescFrec) {
		this.patConsPescFrec = patConsPescFrec;
	}

	public String getPatConsAlimComp() {
		return patConsAlimComp;
	}

	public void setPatConsAlimComp(String patConsAlimComp) {
		this.patConsAlimComp = patConsAlimComp;
	}

	public String getPatConsAlimCompFrec() {
		return patConsAlimCompFrec;
	}

	public void setPatConsAlimCompFrec(String patConsAlimCompFrec) {
		this.patConsAlimCompFrec = patConsAlimCompFrec;
	}

	public String getPatConsLecPol() {
		return patConsLecPol;
	}

	public void setPatConsLecPol(String patConsLecPol) {
		this.patConsLecPol = patConsLecPol;
	}

	public String getPatConsLecPolFrec() {
		return patConsLecPolFrec;
	}

	public void setPatConsLecPolFrec(String patConsLecPolFrec) {
		this.patConsLecPolFrec = patConsLecPolFrec;
	}

	public String getPatConsCarCer() {
		return patConsCarCer;
	}

	public void setPatConsCarCer(String patConsCarCer) {
		this.patConsCarCer = patConsCarCer;
	}

	public String getPatConsCarCerFrec() {
		return patConsCarCerFrec;
	}

	public void setPatConsCarCerFrec(String patConsCarCerFrec) {
		this.patConsCarCerFrec = patConsCarCerFrec;
	}

	public String getPatConsEmb() {
		return patConsEmb;
	}

	public void setPatConsEmb(String patConsEmb) {
		this.patConsEmb = patConsEmb;
	}

	public String getPatConsEmbFrec() {
		return patConsEmbFrec;
	}

	public void setPatConsEmbFrec(String patConsEmbFrec) {
		this.patConsEmbFrec = patConsEmbFrec;
	}

	public String getPatConsMar() {
		return patConsMar;
	}

	public void setPatConsMar(String patConsMar) {
		this.patConsMar = patConsMar;
	}

	public String getPatConsMarFrec() {
		return patConsMarFrec;
	}

	public void setPatConsMarFrec(String patConsMarFrec) {
		this.patConsMarFrec = patConsMarFrec;
	}

	public String getPatConsCarCaza() {
		return patConsCarCaza;
	}

	public void setPatConsCarCaza(String patConsCarCaza) {
		this.patConsCarCaza = patConsCarCaza;
	}

	public String getPatConsCarCazaFrec() {
		return patConsCarCazaFrec;
	}

	public void setPatConsCarCazaFrec(String patConsCarCazaFrec) {
		this.patConsCarCazaFrec = patConsCarCazaFrec;
	}

	public String getPesoTallaEnt() {
		return pesoTallaEnt;
	}

	public void setPesoTallaEnt(String pesoTallaEnt) {
		this.pesoTallaEnt = pesoTallaEnt;
	}

	public String getPesoTallaNin() {
		return pesoTallaNin;
	}

	public void setPesoTallaNin(String pesoTallaNin) {
		this.pesoTallaNin = pesoTallaNin;
	}

	@Override
	public String toString(){
		return this.codigo;
	}
	
	@Override
	public boolean equals(Object other) {
		
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof Segmento))
			return false;
		
		Encuesta castOther = (Encuesta) other;

		return (this.getIdent().equals(castOther.getIdent()));
	}

}
