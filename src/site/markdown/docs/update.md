# Update Statements
Update statements are composed by specifying the table and columns to update, and an optional where clause.  For example:

```java
    UpdateStatement updateStatement = update(animalData)
            .set(bodyWeight).equalTo(record.getBodyWeight())
            .set(animalName).equalToNull()
            .where(id, isIn(1, 5, 7))
            .or(id, isIn(2, 6, 8), and(animalName, isLike("%bat")))
            .or(id, isGreaterThan(60))
            .and(bodyWeight, isBetween(1.0).and(3.0))
            .build()
            .render(RenderingStrategy.MYBATIS3);

    int rows = mapper.update(updateStatement);
```

Notice the `set` method. It is used to set the value of a database column.  There are several options for setting a value:

1. `set(column).equalToNull()` will set a null into a column
2. `set(column).equalToConstant(constant_value)` will set a constant into a column.  The constant_value will be written into the generated update statement exactly as entered
3. `set(column).equalToStringConstant(constant_value)` will set a constant into a column.  The constant_value will be written into the generated update statement surrounded by single quote marks (as an SQL String)
4. `set(column).equalTo(value)` will set a value into a column.  The value will be bound to the SQL statement as a prepared statement parameter
5. `set(column).equalToWhenPresent(value)` will set a value into a column if the value is non-null.  The value of the property will be bound to the SQL statement as a prepared statement parameter.  This is used to generate a "selective" update as defined in MyBatis Generator.

You can also build an update statement without a where clause.  This will update every row in a table.
For example:

```java
    UpdateStatement updateStatement = update(animalData)
            .set(bodyWeight).equalTo(record.getBodyWeight())
            .set(animalName).equalToNull()
            .build()
            .render(RenderingStrategy.MYBATIS3);
``` 

## Annotated Mapper for Update Statements

The UpdateStatement object can be used as a parameter to a MyBatis mapper method directly.  If you
are using an annotated mapper, the update method should look like this:
  
```java
import org.apache.ibatis.annotations.UpdateProvider;
import org.mybatis.dynamic.sql.update.render.UpdateStatement;
import org.mybatis.dynamic.sql.util.SqlProviderAdapter;

...
    @UpdateProvider(type=SqlProviderAdapter.class, method="update")
    int update(UpdateStatement updateStatement);
...

```

## XML Mapper for Update Statements

We do not recommend using an XML mapper for update statements, but if you want to do so the UpdateStatement object can be used as a parameter to a MyBatis mapper method directly.

If you are using an XML mapper, the update method should look like this in the Java interface:
  
```java
import org.mybatis.dynamic.sql.update.render.UpdateStatement;

...
    int update(UpdateeStatement updateStatement);
...

```

The XML element should look like this:

```xml
  <update id="update">
    ${updateStatement}
  </update>
```
