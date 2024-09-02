## spring cloud config server use etcd

### 1、etcd server

1、First start an etcd server, I just start it on my local server and use the default port 2379 and 2380;
2、Then create roles、 name and password paris use etcdcli or other client; In this demo I creat a role ``admin`` and a
user ``etcdAdmin`` which password is ``123456``, then grant the role ``admin`` to ``etcdAdmin``;
3、Authorize roles with key prefix,in this demo , the role ``admin`` has both the read and
write ``spring_etcd、etcd_demo``;

### 2、config server

1、Create a new spring cloud config server project,add jetcd dependency
2、Creat etcd config properties ``EtcdConfigProperties`` and create ``Client`` as a bean managed by spring context;
3、Creat an custom environment Component which implements EnvironmentRepository;

### 3、create config client

1、I create a new spring cloud config client project; you can link to the project
here: [spring-cloud-etcd-client](https://github.com/ypcfly/spring-cloud-etcd-client.git) ;In this project i just create
a very simple project with mysql and jpa,and the configuration is incomplete
in the properties you can there no ``username`` and ``password``, these two configurations are stored in etcd；I also add
a ``String`` field named ``configValue`` with ``@Value`` annotation in ``UserServiceImpl`` class,and you can get this
value via a http request;
