<%@page import = "org.apache.commons.fileupload.FileItem"%>
<%@page import = "org.apache.commons.fileupload.FileItemFactory"%>
<%@page import = "org.apache.commons.fileupload.disk.DiskFileItemFactory"%>
<%@page import = "org.apache.commons.fileupload.servlet.ServletFileUpload"%>
<%@page import = "org.apache.commons.io.FilenameUtils"%>
<%@page import = "java.io.FileOutputStream"%>
<%@page import = "java.util.Iterator"%>
<%@page import = "java.util.List"%>

<%
    // Create a factory for disk-based file items
    FileItemFactory factory = new DiskFileItemFactory();
    // Create a new file upload handler
    ServletFileUpload upload = new ServletFileUpload(factory);
    try {
        // Parse the request
        List items = upload.parseRequest(request);

        // Process the uploaded items
        Iterator iter = items.iterator();

        while (iter.hasNext()) {
            FileItem item = (FileItem) iter.next();

            //handling a normal form-field
            if(item.isFormField()) {
                System.out.println("Got a form field");
                String name = item.getFieldName();
                String value = item.getString();
                System.out.print("Name:"+name+",Value:"+value);

            } else {

                //handling file loads
                System.out.println("Not form field");
                String fieldName = item.getFieldName();
                String fileName = item.getName();
                if (fileName != null) {
                    fileName = FilenameUtils.getName(fileName);
                }

                String contentType = item.getContentType();
                boolean isInMemory = item.isInMemory();
                long sizeInBytes = item.getSize();
                System.out.print("Field Name:"+fieldName +",File Name:"+fileName);
                System.out.print("Content Type:"+contentType
                        +",Is In Memory:"+isInMemory+",Size:"+sizeInBytes);

                byte[] data = item.get();
                fileName = getServletContext()
                        .getRealPath( "/uploadedFiles/" + fileName);
                System.out.print("File name:" +fileName);
                FileOutputStream fileOutSt = new FileOutputStream(fileName);
                fileOutSt.write(data);
                fileOutSt.close();
                out.print("File Uploaded Successfully!");
            }
        }
    } catch(Exception e){
        out.print("File Uploading Failed!" + e.getMessage());
    }
%>