Update Forma Set cCuerpo = CAST(REPLACE(CAST(cCuerpo as NVarchar(MAX)),'COA/FG/XX/____/____/____','&lt;numeroCaso&gt;') AS NText);