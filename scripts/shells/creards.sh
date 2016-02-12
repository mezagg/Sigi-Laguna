#genera un pool de conexiones con el driver de p6spy

asadmin --user admin --port 4848 create-jdbc-connection-pool --driverclassname='com.p6spy.engine.spy.P6SpyDriver' --restype java.sql.Driver --property "user=nsjp:password=nsjp:url='jdbc:p6spy:sqlserver://cloud.lucasianmexico.com:1433;databaseName=coa-sab-pj;'" sigi-pool

