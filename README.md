> Reactor, as the name suggests, is heavily influenced by the well-known [Reactor design pattern](http://en.wikipedia.org/wiki/Reactor_pattern). But it is also influenced by other event-driven design practices, as well as several awesome JVM-based solutions that have been developed over the years. Reactor's goal is to condense these ideas and patterns into a simple and reusable foundation for making event-driven programming much easier.

## About this blogpost
This blogpost will try to teach you the basics of event driven programming using [spring boot](http://projects.spring.io/spring-boot/) and [reactor](https://github.com/reactor/reactor-quickstart). It won't cover every aspect of reactor, nor will one be able to use it as complete reference. I will, however, try to give as much examples as possible in my accompanying code. 

## Accompanying code
This small tutorial is accompanied by a [Github Repository](https://github.com/Qkyrie/spring-boot-reactor-example). Not all content of the code at the repository will be discussed here, so don't forget to check it out later!
The code was compiled and tested using the JDK8, and will therefore require you to have Java 8 to test this application. 

If you find anything in the repository that is unclear, or you something you'd like to see a seperate blogpost of, feel free to file it as an issue in the repository.

### Running the example
Simply download the code - either using git or plain archive downloading. Make sure you have gradle installed. 

	gradle bootRun

## The Code
In this section I'll go over the important components which wire up the example application. It's a full stack application, which means that it'll contain a model, repositories, services and controllers, as wel as a basic view, written in thymeleaf. The frontend won't be a subject of this article, however, feel free to check it out on Github!

### Some Jpa simple entity

    @Entity
    public class LogMessage {
        @Id
        @GeneratedValue
        private Long id;
        private String text;
        private Date logDate;
        @Enumerated(value = STRING)
        private LogCategory category;
    }

    public enum LogCategory {
        DEBUG, ERROR, INFO
    }
    
 What is described here, is a stripped-down version of an entity called **LogMessage**. A LogMessage will just be an entry in our database containing a basic String and some metadata, such as a logDate and an enumerated category.
 
### A Restful repository

    @RepositoryRestResource(collectionResourceRel = "logs", path = "logs")
    public interface LogMessageRepository extends JpaRepository<LogMessage, Long> {
    }

With [Spring Data JPA](http://projects.spring.io/spring-data-jpa/) we can avoid all the boilerplate code which would normally fill our application. Just a simple interface is enough to expose the database in a modern fashion. We also added the **@RepositoryRestResource** annotation, which will later expose the entire repository as a REST-API. This is done by [Spring Data Rest](http://projects.spring.io/spring-data-rest/).

### The Reactor AutoConfiguration

This configuration really speaks for itself. We don't need any special reactor implementation, and therefore, we can count on Spring Boot to provide us with an active Environment, as well as a ReactorAutoConfiguration. Simply Enable it using the **@EnableReactor** annotation.
    
    @Configuration
    @EnableReactor
    public class ReactorConfiguration {
    }

### Wiring up our components - The receiving part

Event driven infrastructures always consist of at least the following two parts: A Sender and a Receiver that will somehow listen or register on a given endpoint. Let's start with some example code of the receiving part.

We'll start with registering on 2 events. 
First of all, we'll register on an event that's triggered once *channel log.(trace|debug)* is being notified. As you'll see we use **reactor.event.selector.Selectors.R**, which is a selector we can use to match a certain [regular expression](http://en.wikipedia.org/wiki/Regular_expression). 

The second selector we'll be using, will be a class-selector. 
**reactor.event.selector.Selectors.T** will react on the notification of a class, in our case *ReactorExampleException*.

We could also be using **reactor.event.selector.Selectors.$**, which is just a simple String-based selector. The syntax highly resembles the JQuery Selectors Syntax


    @Component
    public class ReactorLoggingConfiguration {

        @Autowired
        private Reactor r;

        @Autowired
        private LoggingService loggingService;

        @PostConstruct
        public void onStartUp() {
            r.on(R("log.(trace|debug)"), logForDebug());
            r.on(T(ReactorExampleException.class), logForException());
        }

        private Consumer<Event<ReactorExampleException>> logForException() {
            return logException -> loggingService.log(LogCategory.ERROR, logException.getData().getMessage());
        }


        private Consumer<Event<String>> logForDebug() {
            return logInfoEvent -> loggingService.log(LogCategory.INFO, logInfoEvent.getData());
        }
    }

Ahh, Java 8, isn't it a beauty? Because the callback for an event is wrapped in a Consumer, we can leverage the hassle of creating a Consumer implementation by writing a lambda expression. 
    
###    Wiring up the components - The sending part

The sending part is fairly easy. All you have to do is used an Injected version of **Reactor** - called r here - and invoke the **notify(key, value)**

    @RequestMapping(method = GET)
    public String welcome() {
        r.notify("log.debug", Event.wrap("Wew, someone accessed our page!"));
        return "main";
    }
    

### The result
If we first start up our application, we'll quickly notice that we started with an empty database. By default, Spring boot looks for a DataSource implementation on the classpath. We just added a h2-database, so everyone can test this application without any 3d party necessities, such as a mysql database for example. 

Because we're using spring data rest on our **LogMessageRepository**, it is being fully exposed. Simply browse to the following url to consume the self-explanatory API.
> http://localhost:8080/logs

      {
    "_links" : {
      "self" : {
        "href" : "http://localhost:8080/logs?sort=desc{&page,size}",
        "templated" : true
      }
    },
    "page" : {
      "size" : 20,
      "totalElements" : 0,
      "totalPages" : 0,
      "number" : 0
    }
  }

Of course, our database is still empty. 
Because we added an event that's being triggered once a user visits our homepage, we can trigger it ourselves by visiting the following url.
    
> http://localhost:8080


Upon visiting the rest api again, we can now see a populated database.

  	{
    	"_embedded" : {
      	"logs" : [ {
        	"text" : "Wew, someone accessed our page!",
        	"logDate" : "2014-09-08T09:44:56.024+0000",
        	"category" : "INFO",
        	"_links" : {
          		"self" : {
            	"href" : "http://localhost:8080/logs/1"
          		}
        	}
      	} 	]
    	}
  	}

### What more can we find in the Github repo?
In the repository, I also added a thymeleaf template which connects through websockets to the server. Everytime someone accesses the application by visiting the homepage, a small fragment of the homepage would be updated using JavaScript.

This all is merely an example of how one would use Reactor in a project. If any bugs or questions arise, feel free to mark them as an issue in the repository.

