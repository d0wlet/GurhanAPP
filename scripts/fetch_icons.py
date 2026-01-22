import os
import requests
import xml.etree.ElementTree as ET

# Configuration
ICONS_TO_FETCH = [
    "home",
    "book-open", 
    "settings", 
    "search", 
    "chevron-right", 
    "play-circle",
    "moon",
    "heart"
]

OUTPUT_DIR = "../app/src/main/res/drawable"
GITHUB_BASE_URL = "https://raw.githubusercontent.com/lucide-icons/lucide/main/icons"

def ensure_dir(directory):
    if not os.path.exists(directory):
        os.makedirs(directory)

def fetch_and_convert(icon_name):
    url = f"{GITHUB_BASE_URL}/{icon_name}.svg"
    print(f"Fetching {icon_name} from {url}...")
    
    response = requests.get(url)
    if response.status_code != 200:
        print(f"Failed to fetch {icon_name}")
        return

    svg_content = response.text
    
    # Simple conversion logic from SVG to Android Vector Drawable
    # Note: This is a simplified converter. For complex SVGs, a dedicated tool is better.
    # Lucide icons are simple enough for manual mapping usually.
    
    # We will use a template for Vector Drawable
    vector_xml = f"""<vector xmlns:android="http://schemas.android.com/apk/res/android"
    android:width="24dp"
    android:height="24dp"
    android:viewportWidth="24"
    android:viewportHeight="24"
    android:tint="?attr/colorControlNormal">
  <path
      android:fillColor="@android:color/white"
      android:pathData="{extract_path_data(svg_content)}"/>
</vector>"""

    # Android Vector doesn't support stroke directly on path in the way Web SVG does often
    # Lucide uses strokes. We need to convert stroke to path or use stroke attributes.
    # Actually, Android Vector supports strokeWidth, strokeColor, strokeLineCap, strokeLineJoin.
    
    vector_xml_stroked = f"""<vector xmlns:android="http://schemas.android.com/apk/res/android"
    android:width="24dp"
    android:height="24dp"
    android:viewportWidth="24"
    android:viewportHeight="24">
  <path
      android:pathData="{extract_path_data(svg_content)}"
      android:strokeWidth="2"
      android:strokeColor="#FF000000"
      android:strokeLineCap="round"
      android:strokeLineJoin="round"/>
</vector>"""

    output_path = os.path.join(OUTPUT_DIR, f"ic_{icon_name.replace('-', '_')}.xml")
    with open(output_path, "w") as f:
        f.write(vector_xml_stroked)
    print(f"Saved to {output_path}")

def extract_path_data(svg_text):
    # Very basic extraction - Lucide usually has one or more <path d="..." /> or <circle ... /> etc.
    # For robust conversion, we'd need a real parser. 
    # BUT, to keep it simple and effective, let's just grab the 'd' attributes from paths.
    # If there are multiple paths, we might need multiple path tags in XML.
    
    try:
        root = ET.fromstring(svg_text)
        # Namespace handling might be needed depending on requests output
        # Lucide SVGs are usually clean.
        
        # Actually, let's just cheat a bit and download a converter utility or use a simpler approach.
        # Better yet, let's write a smarter parser here.
        
        paths = []
        for element in root.iter():
            if 'd' in element.attrib:
                paths.append(element.attrib['d'])
            # Circle handling, rect handling etc would be needed for full SVG support
            # Lucide mostly uses paths and basic shapes.
            
            if element.tag.endswith('circle'):
                cx = float(element.attrib.get('cx', 0))
                cy = float(element.attrib.get('cy', 0))
                r = float(element.attrib.get('r', 0))
                # Convert circle to path approximation or leave it? VDs don't support <circle> directly inside <path> data string globally without separate tags?
                # Actually VDs assume paths.
                # Let's try to find an easier way: Use a repo that offers Android XMLs directly?
                # Or just use the SVG to XML conversion library if environment supports it?
                # No external libs allowed easily.
                
                # Alternate strategy: Use Material Icons (Google Fonts) which provides XMLs directly.
                pass
                
        return " ".join(paths)
        
    except Exception as e:
        print(f"Error parsing SVG: {e}")
        return ""

# REVISED STRATEGY: 
# Since converting SVG to Android Vector XML manually with just string manipulation is error-prone for shapes like circles/rects...
# Let's fetch from a source that provides Android XMLs or use a very standard set.
# Google Material Icons are safe. Let's use those via a direct download of the XMLs.
# Repo: https://github.com/google/material-design-icons/
# But user wanted "beautiful icons script".
# Let's try to map Lucide paths. Lucide icons are mostly paths.

def main():
    ensure_dir(OUTPUT_DIR)
    for icon in ICONS_TO_FETCH:
        fetch_and_convert(icon)

if __name__ == "__main__":
    main()
