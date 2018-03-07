package ni.gob.minsa.sivin.utils;

/**
 * Constantes usadas en la base de datos de la aplicacion
 * 
 * @author William Aviles
 * 
 */
public class MainDBConstants {
	
	//Base de datos y tablas
	public static final String DATABASE_NAME = "sivincryp.sqlite3";
	public static final int DATABASE_VERSION = 1;
	
	//Campos metadata
	public static final String recordDate = "recordDate";
	public static final String recordUser = "recordUser";
	public static final String pasive = "pasive";
	public static final String deviceId = "identificador_equipo";
	public static final String estado = "estado";
	
	//Tabla usuarios
	public static final String USER_TABLE = "users";
	//Campos usuarios
	public static final String username = "username";
	public static final String created = "created";
	public static final String modified = "modified";
	public static final String lastAccess = "lastaccess";
	public static final String password = "password";
	public static final String completeName = "completename";
	public static final String email = "email";
	public static final String enabled = "enabled";
	public static final String accountNonExpired = "accountnonexpired";
	public static final String credentialsNonExpired = "credentialsnonexpired";
	public static final String lastCredentialChange = "lastcredentialchange";
	public static final String accountNonLocked = "accountnonlocked";
	public static final String changePasswordNextLogin = "changePasswordNextLogin";
	public static final String createdBy = "createdby";
	public static final String modifiedBy = "modifiedby";
	//Crear tabla usuarios
	public static final String CREATE_USER_TABLE = "create table if not exists "
			+ USER_TABLE + " ("
			+ username + " text not null, "  
			+ created + " date, " 
			+ modified + " date, "
			+ lastAccess + " date, "
			+ password + " text not null, "
			+ completeName + " text, "
			+ email + " text, "
			+ enabled  + " boolean, " 
			+ accountNonExpired  + " boolean, "
			+ credentialsNonExpired  + " boolean, "
			+ lastCredentialChange + " date, "
			+ accountNonLocked  + " boolean, "
			+ changePasswordNextLogin  + " boolean, "
			+ createdBy + " text, "
			+ modifiedBy + " text, "
			+ "primary key (" + username + "));";
	
	//Tabla usuarios roles
	public static final String ROLE_TABLE = "roles";
	//Campos roles
	public static final String role = "role";
	//Crear tabla roles
	public static final String CREATE_ROLE_TABLE = "create table if not exists "
			+ ROLE_TABLE + " ("
			+ username + " text not null, "  
			+ role + " text not null, "
			+ "primary key (" + username + "," + role + "));";	

	//Tabla segmentos
	public static final String SEGMENTO_TABLE = "segmentos";
	//Campos segmentos
	public static final String segmento = "segmento";
	public static final String codigo = "codigo";
	public static final String comunidad = "comunidad";
	public static final String departamento = "departamento";
	public static final String municipio = "municipio";
	public static final String procedencia = "procedencia";
	public static final String region = "region";
	public static final String codigosis = "codigosis";
	public static final String codMun = "codMun";
	public static final String estrato = "estrato";
	public static final String grupo = "grupo";
	public static final String vivParticulares = "vivParticulares";
	public static final String vivInicial = "vivInicial";
	
	//Crear tabla segmentos
	public static final String CREATE_SEGMENTO_TABLE = "create table if not exists "
			+ SEGMENTO_TABLE + " ("
			+ segmento + " text not null, "
			+ codigo + " text not null, "
			+ comunidad + " text, "
			+ departamento + " text, "
			+ municipio + " text, "
			+ procedencia + " text, "
			+ region + " text, "
			+ codigosis + " text, "
			+ codMun + " text, "
			+ estrato + " text, "
			+ grupo + " text, "
			+ vivParticulares + " integer, "
			+ vivInicial + " integer, "
			+ recordDate + " date, " 
			+ recordUser + " text, "
			+ pasive + " text, "
			+ deviceId + " text, "
            + estado + " text not null, "
			+ "primary key ("+ segmento +"));";	
	
	//Tabla encuestadores
	public static final String ENCUESTADOR_TABLE = "encuestadores";
	//Campos segmentos
	public static final String identificador = "identificador";
	public static final String nombre = "nombre";
	
	//Crear tabla encuestadores
	public static final String CREATE_ENCUESTADOR_TABLE = "create table if not exists "
			+ ENCUESTADOR_TABLE + " ("
			+ identificador + " text not null, "
			+ codigo + " text not null, "
			+ nombre + " text, "
			+ recordDate + " date, " 
			+ recordUser + " text, "
			+ pasive + " text, "
			+ deviceId + " text, "
            + estado + " text not null, "
			+ "primary key ("+ identificador +"));";	
	
	//Tabla supervisores
	public static final String SUPERVISOR_TABLE = "supervisores";
	//Campos supervisores
	
	//Crear tabla supervisores
	public static final String CREATE_SUPERVISOR_TABLE = "create table if not exists "
			+ SUPERVISOR_TABLE + " ("
			+ identificador + " text not null, "
			+ codigo + " text not null, "
			+ nombre + " text, "
			+ recordDate + " date, " 
			+ recordUser + " text, "
			+ pasive + " text, "
			+ deviceId + " text, "
            + estado + " text not null, "
			+ "primary key ("+ identificador +"));";

	//Tabla encuestas
	public static final String ENCUESTA_TABLE = "encuestas";
	//Campos encuestas
	public static final String ident = "ident";
	public static final String numEncuesta = "numEncuesta";
	public static final String fechaEntrevista = "fechaEntrevista";
	public static final String latitud = "latitud";
	public static final String longitud = "longitud";
	public static final String jefeFamilia = "jefeFamilia";
	public static final String sexJefeFamilia = "sexJefeFamilia";
	public static final String numPersonas = "numPersonas";
	public static final String encuestador = "encuestador";
	public static final String supervisor = "supervisor";
	//Seccion A
	public static final String agua = "agua";
	public static final String oagua = "oagua";
	public static final String cuartos = "cuartos";
	public static final String lugNecesidades = "lugNecesidades";
	public static final String olugNecesidades = "olugNecesidades";
	public static final String usaCocinar = "usaCocinar";
	public static final String ousaCocinar = "ousaCocinar";
	public static final String articulos = "articulos";
	public static final String oarticulos = "oarticulos";
	//SECCION B
	public static final String conoceFNac = "conoceFNac";
	public static final String fnacEnt = "fnacEnt";
	public static final String edadEnt = "edadEnt";
	public static final String leerEnt = "leerEnt";
	public static final String escribirEnt = "escribirEnt";
	public static final String leeescEnt = "leeescEnt";
	public static final String nivelEnt = "nivelEnt";
	public static final String gradoEnt = "gradoEnt";
	public static final String entRealizada = "entRealizada";
	public static final String entEmb = "entEmb";
	public static final String entEmbUnico = "entEmbUnico";
	public static final String entDioluz = "entDioluz";
	public static final String entHieacfol = "entHieacfol";
	public static final String entMeseshierro = "entMeseshierro";
	public static final String entRecHierro = "entRecHierro";
	public static final String entORecHierro = "entORecHierro";
	public static final String tiemHierroUnd = "tiemHierroUnd";
	public static final String tiemHierro = "tiemHierro";
	public static final String dondeHierro = "dondeHierro";
	public static final String dondeHierroBesp = "dondeHierroBesp";
	public static final String dondeHierroFesp = "dondeHierroFesp";
	public static final String tomadoHierro = "tomadoHierro";
	public static final String vita = "vita";
	public static final String tiempVita = "tiempVita";
	public static final String evitaEmb = "evitaEmb";
	public static final String dondeAntic = "dondeAntic";
	public static final String nuevoEmb = "nuevoEmb";
	public static final String hierro = "hierro";
	public static final String dHierro = "dHierro";
	public static final String numNinos = "numNinos";
	public static final String n1 = "n1";
	public static final String n2 = "n2";
	public static final String n3 = "n3";
	public static final String n4 = "n4";
	public static final String n5 = "n5";
	public static final String n6 = "n6";
	public static final String e1 = "e1";
	public static final String e2 = "e2";
	public static final String e3 = "e3";
	public static final String e4 = "e4";
	public static final String e5 = "e5";
	public static final String e6 = "e6";
	public static final String nselec = "nselec";
	public static final String nomselec = "nomselec";
	public static final String fnacselec = "fnacselec";
	public static final String eselect = "eselect";
	public static final String sexselec = "sexselec";
	public static final String calim = "calim";
	public static final String vcome = "vcome";
	public static final String consalim = "consalim";
	public static final String calimenf = "calimenf";
	public static final String clecheenf = "clecheenf";
	public static final String hierron = "hierron";
	public static final String thierround = "thierround";
	public static final String thierrocant = "thierrocant";
	public static final String fhierro = "fhierro";
	public static final String ghierro = "ghierro";
	public static final String donhierro = "donhierro";
	public static final String donhierrobesp = "donhierrobesp";
	public static final String donhierrofesp = "donhierrofesp";
	public static final String fuhierro = "fuhierro";
	public static final String fuhierror = "fuhierror";
	public static final String fauhierror = "fauhierror";
	public static final String nvita = "nvita";
	public static final String ncvita = "ncvita";
	public static final String tvitaund = "tvitaund";
	public static final String tvitacant = "tvitacant";
	public static final String ndvita = "ndvita";
	public static final String ndvitao = "ndvitao";
	public static final String tdvita = "tdvita";
	public static final String fuvita = "fuvita";
	public static final String fauvita = "fauvita";
	public static final String ncnut = "ncnut";
	public static final String ncnutund = "ncnutund";
	public static final String ncnutcant = "ncnutcant";
	public static final String doncnut = "doncnut";
	public static final String doncnutfotro = "doncnutfotro";
	public static final String parasit = "parasit";
	public static final String cvparas = "cvparas";
	public static final String mParasitario = "mParasitario";
	public static final String otroMpEsp = "otroMpEsp";
	public static final String donMp = "donMp";
	public static final String otroDonMp = "otroDonMp";
	public static final String evitarParasito = "evitarParasito";
	public static final String oEvitarParasito = "oEvitarParasito";
	//SECCION E
	public static final String nlactmat = "nlactmat";
	public static final String sexlmat = "sexlmat";
	public static final String emeseslmat = "emeseslmat";
	public static final String fnaclmat = "fnaclmat";
	public static final String pecho = "pecho";
	public static final String motNopecho = "motNopecho";
	public static final String motNopechoOtro = "motNopechoOtro";
	public static final String dapecho = "dapecho";
	public static final String tiempecho = "tiempecho";
	public static final String tiemmama = "tiemmama";
	public static final String tiemmamaMins = "tiemmamaMins";
	public static final String dospechos = "dospechos";
	public static final String vecespechodia = "vecespechodia";
	public static final String vecespechonoche = "vecespechonoche";
	public static final String elmatexcund = "elmatexcund";
	public static final String elmatexccant = "elmatexccant";
	public static final String ediopechound = "ediopechound";
	public static final String ediopechocant = "ediopechocant";
	public static final String combeb = "combeb";
	public static final String ealimund = "ealimund";
	public static final String ealimcant = "ealimcant";
	public static final String bebeLiq = "bebeLiq";
	public static final String reunionPeso = "reunionPeso";
	public static final String quienReunionPeso = "quienReunionPeso";
	public static final String quienReunionPesoOtro = "quienReunionPesoOtro";
	public static final String assitioReunionPeso = "assitioReunionPeso";
	//SECCION F
	public static final String pesoTallaEnt = "pesoTallaEnt";
	public static final String pesoEnt1 = "pesoEnt1";
	public static final String pesoEnt2 = "pesoEnt2";
	public static final String tallaEnt1 = "tallaEnt1";
	public static final String tallaEnt2 = "tallaEnt2";
	public static final String pesoTallaNin = "pesoTallaNin";
	public static final String pesoNin1 = "pesoNin1";
	public static final String pesoNin2 = "pesoNin2";
	public static final String longNin1 = "longNin1";
	public static final String longNin2 = "longNin2";
	public static final String tallaNin1 = "tallaNin1";
	public static final String tallaNin2 = "tallaNin2";
	//SECCION G
	public static final String msEnt = "msEnt";
	public static final String codMsEnt = "codMsEnt";
	public static final String hbEnt = "hbEnt";
	public static final String msNin = "msNin";
	public static final String codMsNin = "codMsNin";
	public static final String hbNin = "hbNin";
	public static final String moEnt = "moEnt";
	public static final String codMoEnt = "codMoEnt";
	public static final String pan = "pan";
	public static final String sal = "sal";
	public static final String marcaSal = "marcaSal";
	public static final String azucar = "azucar";
	public static final String marcaAzucar = "marcaAzucar";
	//SECCION H
	public static final String patConsAzuc = "patConsAzuc";
	public static final String patConsAzucFrec = "patConsAzucFrec";
	public static final String patConsSal = "patConsSal";
	public static final String patConsSalFrec = "patConsSalFrec";
	public static final String patConsArroz = "patConsArroz";
	public static final String patConsArrozFrec = "patConsArrozFrec";
	public static final String patConsAcei = "patConsAcei";
	public static final String patConsAceiFrec = "patConsAceiFrec";
	public static final String patConsFri = "patConsFri";
	public static final String patConsFriFrec = "patConsFriFrec";
	public static final String patConsCebo = "patConsCebo";
	public static final String patConsCeboFrec = "patConsCeboFrec";
	public static final String patConsChi = "patConsChi";
	public static final String patConsChiFrec = "patConsChiFrec";
	public static final String patConsQue = "patConsQue";
	public static final String patConsQueFrec = "patConsQueFrec";
	public static final String patConsCafe = "patConsCafe";
	public static final String patConsCafeFrec = "patConsCafeFrec";
	public static final String patConsTor = "patConsTor";
	public static final String patConsTorFrec = "patConsTorFrec";
	public static final String patConsCar = "patConsCar";
	public static final String patConsCarFrec = "patConsCarFrec";
	public static final String patConsHue = "patConsHue";
	public static final String patConsHueFrec = "patConsHueFrec";
	public static final String patConsPan = "patConsPan";
	public static final String patConsPanFrec = "patConsPanFrec";
	public static final String patConsBan = "patConsBan";
	public static final String patConsBanFrec = "patConsBanFrec";
	public static final String patConsPanDul = "patConsPanDul";
	public static final String patConsPanDulFrec = "patConsPanDulFrec";
	public static final String patConsPla = "patConsPla";
	public static final String patConsPlaFrec = "patConsPlaFrec";
	public static final String patConsPapa = "patConsPapa";
	public static final String patConsPapaFrec = "patConsPapaFrec";
	public static final String patConsLec = "patConsLec";
	public static final String patConsLecFrec = "patConsLecFrec";
	public static final String patConsSalTom = "patConsSalTom";
	public static final String patConsSalTomFrec = "patConsSalTomFrec";
	public static final String patConsGas = "patConsGas";
	public static final String patConsGasFrec = "patConsGasFrec";
	public static final String patConsCarRes = "patConsCarRes";
	public static final String patConsCarResFrec = "patConsCarResFrec";
	public static final String patConsAvena = "patConsAvena";
	public static final String patConsAvenaFrec = "patConsAvenaFrec";
	public static final String patConsNaran = "patConsNaran";
	public static final String patConsNaranFrec = "patConsNaranFrec";
	public static final String patConsPasta = "patConsPasta";
	public static final String patConsPastaFrec = "patConsPastaFrec";
	public static final String patConsAyote = "patConsAyote";
	public static final String patConsAyoteFrec = "patConsAyoteFrec";
	public static final String patConsMagg = "patConsMagg";
	public static final String patConsMaggFrec = "patConsMaggFrec";
	public static final String patConsFrut = "patConsFrut";
	public static final String patConsFrutFrec = "patConsFrutFrec";
	public static final String patConsRaic = "patConsRaic";
	public static final String patConsRaicFrec = "patConsRaicFrec";
	public static final String patConsMenei = "patConsMenei";
	public static final String patConsMeneiFrec = "patConsMeneiFrec";
	public static final String patConsRepollo = "patConsRepollo";
	public static final String patConsRepolloFrec = "patConsRepolloFrec";
	public static final String patConsZana = "patConsZana";
	public static final String patConsZanaFrec = "patConsZanaFrec";
	public static final String patConsPinolillo = "patConsPinolillo";
	public static final String patConsPinolilloFrec = "patConsPinolilloFrec";
	public static final String patConsOVerd = "patConsOVerd";
	public static final String patConsOVerdFrec = "patConsOVerdFrec";
	public static final String patConsPesc = "patConsPesc";
	public static final String patConsPescFrec = "patConsPescFrec";
	public static final String patConsAlimComp = "patConsAlimComp";
	public static final String patConsAlimCompFrec = "patConsAlimCompFrec";
	public static final String patConsLecPol = "patConsLecPol";
	public static final String patConsLecPolFrec = "patConsLecPolFrec";
	public static final String patConsCarCer = "patConsCarCer";
	public static final String patConsCarCerFrec = "patConsCarCerFrec";
	public static final String patConsEmb = "patConsEmb";
	public static final String patConsEmbFrec = "patConsEmbFrec";
	public static final String patConsMar = "patConsMar";
	public static final String patConsMarFrec = "patConsMarFrec";
	public static final String patConsCarCaza = "patConsCarCaza";
	public static final String patConsCarCazaFrec = "patConsCarCazaFrec";
	
	//Crear tabla usuarios encuestas
	public static final String CREATE_ENCUESTA_TABLE = "create table if not exists "
			+ ENCUESTA_TABLE + " ("
			+ ident + " text not null, "
			+ codigo + " text not null, "
			+ segmento + " text not null, "
			+ numEncuesta + " integer, "
			+ fechaEntrevista + " date, "
			+ latitud + " text, "
			+ longitud + " text, "
			+ jefeFamilia + " text, "
			+ sexJefeFamilia + " text, "
			+ numPersonas + " integer, "
			+ encuestador + " text, "
			+ supervisor + " text, "
			+ agua + " text, "
			+ oagua + " text, "
			+ cuartos + " integer, "
			+ lugNecesidades + " text, "
			+ olugNecesidades + " text, "
			+ usaCocinar + " text, "
			+ ousaCocinar + " text, "
			+ articulos + " text, "
			+ oarticulos + " text, "
			+ conoceFNac + " text, "
			+ fnacEnt + " date, "
			+ edadEnt + " text, "
			+ leerEnt + " text, "
			+ escribirEnt + " text, "
			+ leeescEnt + " text, "
			+ nivelEnt + " text, "
			+ gradoEnt + " text, "
			+ entRealizada + " text, "
			+ entEmb + " text, "
			+ entEmbUnico + " text, "
			+ entDioluz + " text, "
			+ entHieacfol + " text, "
			+ entMeseshierro + " text, "
			+ entRecHierro + " text, "
			+ entORecHierro + " text, "
			+ tiemHierroUnd + " text, "
			+ tiemHierro + " text, "
			+ dondeHierro + " text, "
			+ dondeHierroBesp + " text, "
			+ dondeHierroFesp + " text, "
			+ tomadoHierro + " text, "
			+ vita + " text, "
			+ tiempVita + " text, "
			+ evitaEmb + " text, "
			+ dondeAntic + " text, "
			+ nuevoEmb + " text, "
			+ hierro + " text, "
			+ dHierro + " text, "
			+ numNinos + " text, "
			+ n1 + " text, "
			+ n2 + " text, "
			+ n3 + " text, "
			+ n4 + " text, "
			+ n5 + " text, "
			+ n6 + " text, "
			+ e1 + " text, "
			+ e2 + " text, "
			+ e3 + " text, "
			+ e4 + " text, "
			+ e5 + " text, "
			+ e6 + " text, "
			+ nselec + " text, "
			+ nomselec + " text, "
			+ fnacselec + " date, "
			+ eselect + " text, "
			+ sexselec + " text, "
			+ calim + " text, "
			+ vcome + " text, "
			+ consalim + " text, "
			+ calimenf + " text, "
			+ clecheenf + " text, "
			+ hierron + " text, "
			+ thierround + " text, "
			+ thierrocant + " text, "
			+ fhierro + " text, "
			+ ghierro + " text, "
			+ donhierro + " text, "
			+ donhierrobesp + " text, "
			+ donhierrofesp + " text, "
			+ fuhierro + " text, "
			+ fuhierror + " date, "
			+ fauhierror + " date, "
			+ nvita + " text, "
			+ ncvita + " text, "
			+ tvitaund + " text, "
			+ tvitacant + " text, "
			+ ndvita + " text, "
			+ ndvitao + " text, "
			+ tdvita + " text, "
			+ fuvita + " date, "
			+ fauvita + " date, "
			+ ncnut + " text, "
			+ ncnutund + " text, "
			+ ncnutcant + " text, "
			+ doncnut + " text, "
			+ doncnutfotro + " text, "
			+ parasit + " text, "
			+ cvparas + " text, "
			+ mParasitario + " text, "
			+ otroMpEsp + " text, "
			+ donMp + " text, "
			+ otroDonMp + " text, "
			+ evitarParasito + " text, "
			+ oEvitarParasito + " text, "
			+ nlactmat + " text, "
			+ sexlmat + " text, "
			+ emeseslmat + " text, "
			+ fnaclmat + " date, "
			+ pecho + " text, "
			+ motNopecho + " text, "
			+ motNopechoOtro + " text, "
			+ dapecho + " text, "
			+ tiempecho + " text, "
			+ tiemmama + " text, "
			+ tiemmamaMins + " text, "
			+ dospechos + " text, "
			+ vecespechodia + " text, "
			+ vecespechonoche + " text, "
			+ elmatexcund + " text, "
			+ elmatexccant + " text, "
			+ ediopechound + " text, "
			+ ediopechocant + " text, "
			+ combeb + " text, "
			+ ealimund + " text, "
			+ ealimcant + " text, "
			+ bebeLiq + " text, "
			+ reunionPeso + " text, "
			+ quienReunionPeso + " text, "
			+ quienReunionPesoOtro + " text, "
			+ assitioReunionPeso + " text, "
			+ pesoTallaEnt + " text, "
			+ pesoEnt1 + " real, "
			+ pesoEnt2 + " real, "
			+ tallaEnt1 + " real, "
			+ tallaEnt2 + " real, "
			+ pesoTallaNin + " text, "
			+ pesoNin1 + " real, "
			+ pesoNin2 + " real, "
			+ longNin1 + " real, "
			+ longNin2 + " real, "
			+ tallaNin1 + " real, "
			+ tallaNin2 + " real, "
			+ msEnt + " text, "
			+ codMsEnt + " text, "
			+ hbEnt + " real, "
			+ msNin + " text, "
			+ codMsNin + " text, "
			+ hbNin + " real, "
			+ moEnt + " text, "
			+ codMoEnt + " text, "
			+ pan + " text, "
			+ sal + " text, "
			+ marcaSal + " text, "
			+ azucar + " text, "
			+ marcaAzucar + " text, "
			+ patConsAzuc + " text, "
			+ patConsAzucFrec + " text, "
			+ patConsSal + " text, "
			+ patConsSalFrec + " text, "
			+ patConsArroz + " text, "
			+ patConsArrozFrec + " text, "
			+ patConsAcei + " text, "
			+ patConsAceiFrec + " text, "
			+ patConsFri + " text, "
			+ patConsFriFrec + " text, "
			+ patConsCebo + " text, "
			+ patConsCeboFrec + " text, "
			+ patConsChi + " text, "
			+ patConsChiFrec + " text, "
			+ patConsQue + " text, "
			+ patConsQueFrec + " text, "
			+ patConsCafe + " text, "
			+ patConsCafeFrec + " text, "
			+ patConsTor + " text, "
			+ patConsTorFrec + " text, "
			+ patConsCar + " text, "
			+ patConsCarFrec + " text, "
			+ patConsHue + " text, "
			+ patConsHueFrec + " text, "
			+ patConsPan + " text, "
			+ patConsPanFrec + " text, "
			+ patConsBan + " text, "
			+ patConsBanFrec + " text, "
			+ patConsPanDul + " text, "
			+ patConsPanDulFrec + " text, "
			+ patConsPla + " text, "
			+ patConsPlaFrec + " text, "
			+ patConsPapa + " text, "
			+ patConsPapaFrec + " text, "
			+ patConsLec + " text, "
			+ patConsLecFrec + " text, "
			+ patConsSalTom + " text, "
			+ patConsSalTomFrec + " text, "
			+ patConsGas + " text, "
			+ patConsGasFrec + " text, "
			+ patConsCarRes + " text, "
			+ patConsCarResFrec + " text, "
			+ patConsAvena + " text, "
			+ patConsAvenaFrec + " text, "
			+ patConsNaran + " text, "
			+ patConsNaranFrec + " text, "
			+ patConsPasta + " text, "
			+ patConsPastaFrec + " text, "
			+ patConsAyote + " text, "
			+ patConsAyoteFrec + " text, "
			+ patConsMagg + " text, "
			+ patConsMaggFrec + " text, "
			+ patConsFrut + " text, "
			+ patConsFrutFrec + " text, "
			+ patConsRaic + " text, "
			+ patConsRaicFrec + " text, "
			+ patConsMenei + " text, "
			+ patConsMeneiFrec + " text, "
			+ patConsRepollo + " text, "
			+ patConsRepolloFrec + " text, "
			+ patConsZana + " text, "
			+ patConsZanaFrec + " text, "
			+ patConsPinolillo + " text, "
			+ patConsPinolilloFrec + " text, "
			+ patConsOVerd + " text, "
			+ patConsOVerdFrec + " text, "
			+ patConsPesc + " text, "
			+ patConsPescFrec + " text, "
			+ patConsAlimComp + " text, "
			+ patConsAlimCompFrec + " text, "
			+ patConsLecPol + " text, "
			+ patConsLecPolFrec + " text, "
			+ patConsCarCer + " text, "
			+ patConsCarCerFrec + " text, "
			+ patConsEmb + " text, "
			+ patConsEmbFrec + " text, "
			+ patConsMar + " text, "
			+ patConsMarFrec + " text, "
			+ patConsCarCaza + " text, "
			+ patConsCarCazaFrec + " text, "
			+ recordDate + " date, " 
			+ recordUser + " text, "
			+ pasive + " text, "
			+ deviceId + " text, "
            + estado + " text not null, "
			+ "primary key ("+ ident +"));";	
	
	//Tabla mensajes
	public static final String MESSAGES_TABLE = "mensajes";
	//Campos mensajes
	public static final String messageKey = "messageKey";
	public static final String catRoot = "catRoot";
	public static final String catKey = "catKey";
	public static final String isCat = "isCat";
	public static final String order = "orden";
	public static final String spanish = "spanish";
	public static final String english = "english";
	
	//Crear tabla mensajes
	public static final String CREATE_MESSAGES_TABLE = "create table if not exists "
			+ MESSAGES_TABLE + " ("
			+ messageKey + " text not null, "
			+ catRoot + " text , "
			+ catKey + " text, "
			+ isCat + " text , "
			+ order + " integer , "
			+ spanish + " text not null, "
			+ english + " text , "
			+ MainDBConstants.pasive + " text, "
			+ "primary key (" + messageKey + "));";	

}
