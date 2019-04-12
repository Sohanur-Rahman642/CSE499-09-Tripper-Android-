#! /bin/bash
while :
do
    echo "Enter your Search Criteria to search or write 'Exit' to end the program"
    echo "'AQ' to search by Air Quality"
    echo "'OH' to search by O3 Hour"
    echo "'OQ' to search by O3 Quality"
    echo "'OV' to search by O3 Value"
    echo "'NV' to search by NO2 Value"
    echo "'PV' to search by PM10 Value"
    
    echo "'Exit' to exit"
    read input

        case $input in

        Exit)
            break ;;

 	    AQ)
            echo "Please enter your search term for Air Quality"
            read "keyword"
            if readingFile=$(grep -w   "$keyword"  air_quality_Nov2017.csv)
                then echo "$readingFile"
            else
                echo "Sorry, not found"
            fi ;;


        

        OH)
            echo "Please enter your search term for O3 Hour"
            read "keyword"
            if readingFile=$(grep -w   "$keyword"  air_quality_Nov2017.csv)
                then echo "$readingFile"
            else
                echo "Sorry, not found"
            fi ;;

        OQ)
            echo "Please enter your search term for O3 Quality"
            read "keyword"
            if readingFile=$(grep -w   "$keyword" air_quality_Nov2017.csv)
                then echo "$readingFile"
            else
                echo "Sorry, not found"
            fi ;;

        OV)
            echo "Please enter your search term for O3 Value"
            read "keyword"
            if readingFile=$(grep -w   "$keyword" air_quality_Nov2017.csv)
                then echo "$readingFile"
            else
                echo "Sorry, not found"
            fi ;;

         NV)
            echo "Please enter your search term number of NO2 Value"
            read keyword
            if readingFile=$(grep -w "$keyword"air_quality_Nov2017.csv)
                then echo "$readingFile"
            else
                echo "Sorry, not found"
            fi ;;

        PV)
            echo "Please enter your search term for PM10 Value"
            read keyword
            if readingFile=$(awk -F',' '$7~/^'$keyword'/' air_quality_Nov2017.csv)
                then echo "$readingFile"
            else
                echo "Sorry, not found"
            fi ;;

        *)
            echo "Enter valid input";;
        esac
done
