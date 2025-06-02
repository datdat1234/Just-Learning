## Semantic Forms

When creating a form, you can use the following elements: `<form>`, `<label>`, `<input>`, `<textarea>`, and `<button>`.

### Labels

Provide labels to describe the purpose of all form control; linking for and id:

```template
<label for="name">Name: </label>
<input type="text" name="name" id="name" v-model="name" />
```

### Placeholder​

Avoid using placeholders as they can confuse many users.

One of the issues with placeholders is that they don't meet the color contrast criteria by default; fixing the color contrast makes the placeholder look like pre-populated data in the input fields. Looking at the following example, you can see that the Last Name placeholder which meets the color contrast criteria looks like pre-populated data:

<div align="center">

![alt text](../image/accessible-placeholder.png)

</div>

It is best to provide all the information the user needs to fill out forms outside any inputs.

### Instructions

When adding instructions for your input fields, make sure to link it correctly to the input. You can provide additional instructions and bind multiple ids inside an aria-labelledby. This allows for more flexible design.

Alternatively, you can attach the instructions to the input with aria-describedby.

### Hiding Content

Usually it is not recommended to visually hide labels, even if the input has an accessible name. However, if the functionality of the input can be understood with surrounding content, then we can hide the visual label.

### Buttons

When using buttons inside a form, you must set the type to prevent submitting the form. You can also use an input to create buttons.

## Standards

## Resources

### Documentation

### Assistive Technologies

### Testing

### Users