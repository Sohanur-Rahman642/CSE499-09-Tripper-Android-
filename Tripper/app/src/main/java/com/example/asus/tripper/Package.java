package com.example.asus.tripper;

public class Package {




        private int id;
        private String title;
        private String shortDesc;
        private String packageTitle;
        private int imageTitle;
        private int imagePackage;


        public Package(int id,int imageTitle,String title,int imagePackage, String packageTitle,String shortDesc){

            this.id= id;
            this.imageTitle = imageTitle;
            this.title = title;
            this.imagePackage = imagePackage;
            this.packageTitle = packageTitle;
            this.shortDesc = shortDesc;




        }

        public int getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public String getShortDesc() {
            return shortDesc;
        }

        public String getPackageTitle() {
            return packageTitle;
        }

        public int getImageTitle() {
            return imageTitle;
        }

        public int getImagePackage() {
            return imagePackage;
        }



}
