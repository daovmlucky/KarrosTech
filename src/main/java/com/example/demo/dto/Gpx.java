package com.example.demo.dto;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;




@XmlRootElement(name = "gpx")
public class Gpx {
	

	@XmlAttribute(required = true)
	private String xmlns;
	@XmlAttribute(required = true)
	private String version;
    @XmlAttribute(required = true)
    private String creator;
    
    @XmlElement
    Metadata metadata;
	
    @XmlElement
    List<Gpx.Wpt> wpt;
    
    @XmlElement
    List<Gpx.Trk> trk;
    
    public Gpx() {
		super();
		wpt = new ArrayList<>();
		trk = new ArrayList<>();
	}
    
    public void addWaypoint(Wpt wayPoint) {
    	wpt.add(wayPoint);
    }
    
	public String getXmlns() {
		return xmlns;
	}

	public void setXmlns(String xmlns) {
		this.xmlns = xmlns;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Metadata getMetadata() {
		return metadata;
	}

	public void setMetadata(Metadata metadata) {
		this.metadata = metadata;
	}

	public List<Gpx.Wpt> getWpt() {
		if (wpt == null) {
			wpt = new ArrayList<Gpx.Wpt>();
        }
        return this.wpt;
	}

	public void setWpt(List<Gpx.Wpt> wpt) {
		this.wpt = wpt;
	}

	public List<Gpx.Trk> getTrk() {
		if (trk == null) {
			trk = new ArrayList<Gpx.Trk>();
        }
        return this.trk;
	}

	public void setTrk(List<Gpx.Trk> trk) {
		this.trk = trk;
	}



	public static class Trk {
    	
		@XmlElement
    	private String name;
    	@XmlElement
    	private String cmt;
    	@XmlElement
    	private String desc;
    	@XmlElement
    	private String src;
    	@XmlElement
    	private Link link;
    	@XmlSchemaType(name = "nonNegativeInteger")
        private BigInteger number;
    	@XmlElement
    	List<Gpx.Trk.Trkseg> trkseg;
    	

    	public Trk() {
			super();
			trkseg = new ArrayList<>();
		}
    	

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getCmt() {
			return cmt;
		}

		public void setCmt(String cmt) {
			this.cmt = cmt;
		}

		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
		}

		public String getSrc() {
			return src;
		}

		public void setSrc(String src) {
			this.src = src;
		}

		public Link getLink() {
			return link;
		}

		public void setLink(Link link) {
			this.link = link;
		}

		public BigInteger getNumber() {
			return number;
		}

		public void setNumber(BigInteger number) {
			this.number = number;
		}

		public List<Gpx.Trk.Trkseg> getTrkseg() {
			if (trkseg == null) {
				trkseg = new ArrayList<Gpx.Trk.Trkseg>();
            }
            return this.trkseg;
		}

		public void setTrkseg(List<Gpx.Trk.Trkseg> trkseg) {
			this.trkseg = trkseg;
		}

		public static class Trkseg {
    		
    		@XmlElement
			private List<Gpx.Trk.Trkseg.Trkpt> trkpt;
    		

			public Trkseg() {
				super();
				trkpt = new ArrayList<Gpx.Trk.Trkseg.Trkpt>();
			}

			public List<Gpx.Trk.Trkseg.Trkpt> getTrkpt() {
				if (trkpt == null) {
					trkpt = new ArrayList<Gpx.Trk.Trkseg.Trkpt>();
                }
                return this.trkpt;
			}

			public void setTrkpt(List<Gpx.Trk.Trkseg.Trkpt> trkpt) {
				this.trkpt = trkpt;
			}

			public static class Trkpt {
                @XmlAttribute(required = true)
                private BigDecimal lat;
                @XmlAttribute(required = true)
                private BigDecimal lon;
                @XmlElement
                private BigDecimal ele;
                @XmlSchemaType(name = "dateTime")
                @XmlElement
                private XMLGregorianCalendar time;
 
				public Trkpt() {
					super();
				}
				public BigDecimal getLat() {
					return lat;
				}
				public void setLat(BigDecimal lat) {
					this.lat = lat;
				}
				public BigDecimal getLon() {
					return lon;
				}
				public void setLon(BigDecimal lon) {
					this.lon = lon;
				}
				public BigDecimal getEle() {
					return ele;
				}
				public void setEle(BigDecimal ele) {
					this.ele = ele;
				}
				public XMLGregorianCalendar getTime() {
					return time;
				}
				public void setTime(XMLGregorianCalendar time) {
					this.time = time;
				}
                
                
			}
		}
	}

    public static class Wpt{
    	@XmlAttribute(required = true)
        private BigDecimal lat;
        @XmlAttribute(required = true)
        private BigDecimal lon;
        @XmlElement
        private BigDecimal ele;
        @XmlSchemaType(name = "dateTime")
        private XMLGregorianCalendar time;
        @XmlElement
        private BigDecimal magvar;
        @XmlElement
        private BigDecimal geoidheight;
        @XmlElement
        private String name;
        @XmlElement
        private String cmt;
        @XmlElement
        private String desc;
        @XmlElement
        private String src;
        @XmlElement
        Link link;
        @XmlElement
        private String sym;
        @XmlElement
        private String type;
        @XmlElement
        private String fix;
        @XmlSchemaType(name = "nonNegativeInteger")
        @XmlElement
        private BigInteger sat;
        @XmlElement
        private BigDecimal hdop;
        @XmlElement
        private BigDecimal vdop;
        @XmlElement
        private BigDecimal pdop;
        @XmlElement
        private BigDecimal ageofdgpsdata;
        @XmlElement
        private Integer dgpsid;
        
		public Wpt(BigDecimal lat,BigDecimal lon) {
			super();
		}
		
		public BigDecimal getLat() {
			return lat;
		}
		public void setLat(BigDecimal lat) {
			this.lat = lat;
		}
		public BigDecimal getLon() {
			return lon;
		}
		public void setLon(BigDecimal lon) {
			this.lon = lon;
		}
		public BigDecimal getEle() {
			return ele;
		}
		public void setEle(BigDecimal ele) {
			this.ele = ele;
		}
		public XMLGregorianCalendar getTime() {
			return time;
		}
		public void setTime(XMLGregorianCalendar time) {
			this.time = time;
		}
		public BigDecimal getMagvar() {
			return magvar;
		}
		public void setMagvar(BigDecimal magvar) {
			this.magvar = magvar;
		}
		public BigDecimal getGeoidheight() {
			return geoidheight;
		}
		public void setGeoidheight(BigDecimal geoidheight) {
			this.geoidheight = geoidheight;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getCmt() {
			return cmt;
		}
		public void setCmt(String cmt) {
			this.cmt = cmt;
		}
		public String getDesc() {
			return desc;
		}
		public void setDesc(String desc) {
			this.desc = desc;
		}
		public String getSrc() {
			return src;
		}
		public void setSrc(String src) {
			this.src = src;
		}
		public Link getLink() {
			return link;
		}
		public void setLink(Link link) {
			this.link = link;
		}
		public String getSym() {
			return sym;
		}
		public void setSym(String sym) {
			this.sym = sym;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getFix() {
			return fix;
		}
		public void setFix(String fix) {
			this.fix = fix;
		}
		public BigInteger getSat() {
			return sat;
		}
		public void setSat(BigInteger sat) {
			this.sat = sat;
		}
		public BigDecimal getHdop() {
			return hdop;
		}
		public void setHdop(BigDecimal hdop) {
			this.hdop = hdop;
		}
		public BigDecimal getVdop() {
			return vdop;
		}
		public void setVdop(BigDecimal vdop) {
			this.vdop = vdop;
		}
		public BigDecimal getPdop() {
			return pdop;
		}
		public void setPdop(BigDecimal pdop) {
			this.pdop = pdop;
		}
		public BigDecimal getAgeofdgpsdata() {
			return ageofdgpsdata;
		}
		public void setAgeofdgpsdata(BigDecimal ageofdgpsdata) {
			this.ageofdgpsdata = ageofdgpsdata;
		}
		public Integer getDgpsid() {
			return dgpsid;
		}
		public void setDgpsid(Integer dgpsid) {
			this.dgpsid = dgpsid;
		}
    }
    
    public static class Metadata{
    	@XmlElement
    	private String name;
    	@XmlElement
    	private String desc;
    	@XmlElement
    	private String author;
    	@XmlElement
    	private String email;
    	@XmlElement
    	private Link link;
    	@XmlSchemaType(name = "dateTime")
    	@XmlElement
    	private XMLGregorianCalendar time;
    	@XmlElement
    	private String keywords;
    	@XmlElement
    	private BoundsType bounds;
    	
		public Metadata() {
			super();
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
		}

		public String getAuthor() {
			return author;
		}

		public void setAuthor(String author) {
			this.author = author;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public Link getLink() {
			return link;
		}

		public void setLink(Link link) {
			this.link = link;
		}

		public XMLGregorianCalendar getTime() {
			return time;
		}

		public void setTime(XMLGregorianCalendar time) {
			this.time = time;
		}

		public String getKeywords() {
			return keywords;
		}

		public void setKeywords(String keywords) {
			this.keywords = keywords;
		}

		public BoundsType getBounds() {
			return bounds;
		}

		public void setBounds(BoundsType bounds) {
			this.bounds = bounds;
		}
	
    }
    
    public static class Link{
		
    	@XmlAttribute
		private String href;
    	@XmlElement
		private String text;
		
		public Link() {
			super();
		}
		public String getHref() {
			return href;
		}
		
		public void setHref(String href) {
			this.href = href;
		}
		public String getText() {
			return text;
		}
		
		public void setText(String text) {
			this.text = text;
		}
    	 
    	 
    }
    
    public static class BoundsType {
    	@XmlAttribute(required = true)
        protected BigDecimal minlat;
        @XmlAttribute(required = true)
        protected BigDecimal minlon;
        @XmlAttribute(required = true)
        protected BigDecimal maxlat;
        @XmlAttribute(required = true)
        protected BigDecimal maxlon;
        
		public BoundsType(BigDecimal minlat, BigDecimal minlon, BigDecimal maxlat, BigDecimal maxlon) {
			super();
			this.minlat = minlat;
			this.minlon = minlon;
			this.maxlat = maxlat;
			this.maxlon = maxlon;
		}
		public BoundsType() {
			super();
		}
		public BigDecimal getMinlat() {
			return minlat;
		}
		public void setMinlat(BigDecimal minlat) {
			this.minlat = minlat;
		}
		public BigDecimal getMinlon() {
			return minlon;
		}
		public void setMinlon(BigDecimal minlon) {
			this.minlon = minlon;
		}
		public BigDecimal getMaxlat() {
			return maxlat;
		}
		public void setMaxlat(BigDecimal maxlat) {
			this.maxlat = maxlat;
		}
		public BigDecimal getMaxlon() {
			return maxlon;
		}
		public void setMaxlon(BigDecimal maxlon) {
			this.maxlon = maxlon;
		}

        
    }
    

}
