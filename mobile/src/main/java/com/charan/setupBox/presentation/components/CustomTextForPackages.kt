package com.charan.setupBox.presentation.components
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun CustomTextForPackages(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String,
    packages: List<String?>
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier=Modifier

    ) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(text = label) },
            modifier = Modifier
                .fillMaxWidth()
                .then(modifier)
                .menuAnchor(MenuAnchorType.PrimaryEditable)
                ,

            singleLine = true
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier=Modifier


        ) {
            packages.forEach { packageName ->
                packageName?.let {
                    DropdownMenuItem(

                        text = { Text(it) },
                        onClick = {
                            onValueChange(it)
                            expanded = false
                        },

                    )
                }
            }
        }
    }
}



