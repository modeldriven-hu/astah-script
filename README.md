# Astah Script Plugin-in

This enables you to use Groovy language scripting for Astah.

# Supported features

* Groovy language support
* Display result in text area
* Display result in grid
* Export grid result as CSV file
* Fully-fledged editor component
* Syntax highlight
* Jump to line in case of error
* History support
* Filter result

# How to write scripts in Groovy?

## Overview

The following document the Groovy language support provided by the plugin.

## What is Groovy?

Groovy is a powerful, optionally typed and dynamic language, with static-typing and static compilation capabilities, for
the Java platform aimed at improving developer productivity thanks to a concise, familiar and easy to learn syntax.

For further information please visit https://groovy-lang.org/.

## How to execute a script?

To execute a script enter it into the code editor area and press the  `Execute` button.

## How does it work?

The script from the code editor will be parsed and executed by a Groovy engine.

## Default variables

For each script some default variables are injected.

The following variables are provided currently:

* `api`: An `AstahAPI.getAstahAPI()` for model navigation.
* `helper`: A `hu.modeldriven.astah.script.common.script` supporting class

The variables can be referenced in the script as follows:

```

import com.change_vision.jude.api.inf.model.IClass;

def accessor = api.getProjectAccessor();
def classes = accessor.findElements(IClass.class);

def list = []

for (def clazz in classes){
list << clazz
}

return helper.tabularResult(
	list,
	[id: "Id", name:"Name"]
)
```

# How to display results?

In order to display the result of the script it has to return the data to be displayed. There are various ways of it.

## Return data using the variable

Example:

```
    def x = 5
    x
```

Will return 5.

## Return data using the return statement

Example:

```
    def x = 5
    return x
```

Will also return 5.

# How to display data in the table?

In case you would like to display a data in a tabular format, you need to return it in a special format.

## Return data as list

If data is to be returned as list it is automatically rendered in the table of a single column.

```
return [1,2,3,4,5]
```

This will display a table with a single column with the provided values.

## Return data in tabular format

If data is to be returned as a grid use `helper.tabularResult(Object list, Map<String, String> fields)`
helper function.

### List

List contains a list of objects.

### Fields

Fields contains key-value pairs, where the key is the property of the object (like `name`) and the value is the column
name to be displayed.

```
class MyClass{
	String id;
	String name;
}

// create a new object from the class

def myClass = new MyClass();
myClass.id = '123';
myClass.name = "Hello world";

// create an empty list

elements = []

// add the object to the list

elements << myClass

// return the list of object as a tabular data

return helper.tabularResult(
    elements,
    [id: "Id", name: "Name"]
)

```
