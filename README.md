# astah-script
Astah script editor

# Example

import com.change_vision.jude.api.inf.model.IClass;

def accessor = api.getProjectAccessor();
def classes = accessor.findElements(IClass.class);

classes[0].name